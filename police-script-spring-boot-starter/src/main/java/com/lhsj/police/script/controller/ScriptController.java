package com.lhsj.police.script.controller;


import com.google.common.collect.Maps;
import com.lhsj.police.core.io.ReFiles;
import com.lhsj.police.core.json.ReJsons;
import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.net.RestClient;
import com.lhsj.police.core.response.Response;
import com.lhsj.police.core.text.ReEncodes;
import com.lhsj.police.script.configuration.ScriptProperties;
import com.lhsj.police.script.model.ScriptParam;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.constant.Constants.EMPTY;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings("unused")
@RequestMapping("/police/script")
public class ScriptController implements ApplicationContextAware {

    private ApplicationContext context;
    @Resource
    private ScriptProperties   properties;

    /**
     * 本地执行，URL只负责触发，所以要执行脚本，必须登录到目标机器
     * 在执行路径下，录入脚本，URL触发执行
     */
    @GetMapping("/single/local")
    @ResponseBody
    public void local(HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam("password") String password) throws IOException, ResourceException, ScriptException {
        isTrue(isNotBlank(properties.getPassword()), "password blank");
        isTrue(Objects.equals(properties.getPassword(), password), "password invalid");

        Object result = executeScript();
        response.getWriter().println(ReJsons.obj2String(result));
    }

    /**
     * 远程触发执行
     * <p>
     * 脚本内容，来源request
     */
    @GetMapping("/single/complex")
    @ResponseBody
    public Response<?> complex(HttpServletRequest request, HttpServletResponse response) {
        try {
            ScriptParam param = ScriptParam.of(request);

            validate(param);

            if (param.getRemote()) {
                return Response.<String>ofSuccess().data(invokeRemote(param));
            } else {
                return Response.<String>ofSuccess().data(invokeLocal(param.getScript()));
            }
        } catch (Throwable e) {
            ReLogs.warn("ScriptController.postScript", e);
            return Response.<String>ofError().msg(e.toString());
        }
    }

    private String invokeLocal(String script) throws IOException, ResourceException, ScriptException {
        script = touchScriptFileIfNeeded(script);

        Object result = executeScript();

        ReLogs.info("ScriptController.postScript, result = {}, script = {}", ReJsons.obj2String(result), script);

        return ReJsons.obj2String(result);
    }

    private String invokeRemote(ScriptParam param) {
        final String url = "http://" + param.getHost() + ":" + param.getPort() + "/police/script/single/complex";
        final String encodeScript = ReEncodes.encodeBase64UrlSafe(param.getScript().getBytes());

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("remote", "false");
        paramMap.put("encode", "true");
        paramMap.put("script", encodeScript);
        paramMap.put("password", param.getPassword());
        Map<String, Object> headerMap = Maps.newHashMap();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");

        Response<?> response = RestClient.instance().get(url, paramMap, headerMap, Response.class);

        ReLogs.info("ScriptController.postScript, host = {}, result = {}, script = {}",
                param.getHost(), ReJsons.obj2String(response), param.getScript());

        isTrue(nonNull(response), "invokeRemote error, response null，host = {}, script = {}",
                param.getHost(), param.getScript());
        isTrue(response.getSuccess(), "invokeRemote error, host = {}, errorMsg = {}, script = {}",
                param.getHost(), response.getMsg(), param.getScript());

        return (String) response.getData();
    }

    private Object executeScript() throws IOException, ResourceException, ScriptException {
        final String absolutePath = properties.getAbsolutePath();
        GroovyScriptEngine gse = new GroovyScriptEngine(getScriptRoots(absolutePath));
        Binding binding = new Binding();
        binding.setVariable(properties.getContextName(), context);
        return gse.run(getScriptName(absolutePath), binding);
    }

    public String[] getScriptRoots(String absolutePath) {
        return new String[]{absolutePath.substring(0, absolutePath.lastIndexOf("/"))};
    }

    public String getScriptName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
    }

    private void validate(ScriptParam param) {
        isTrue(hostPermitted(param.getHost()), "host invalid, script forbidden, host = {}", param.getHost());
        isTrue(Objects.equals(param.getPassword(), properties.getPassword()), "password invalid");
    }

    private boolean hostPermitted(String uri) {
        if (isBlank(uri)) {
            return false;
        }

        final List<String> uris = properties.getHosts();
        if (isEmpty(properties.getHosts())) {
            return true;
        }

        return uris.contains(uri);
    }

    private String touchScriptFileIfNeeded(String script) throws IOException {
        String absolutePath = properties.getAbsolutePath();

        if (ReFiles.isFileExists(absolutePath)) {
            ReFiles.write(EMPTY, new File(absolutePath));
        } else {
            ReFiles.touch(absolutePath);
        }

        // 写入脚本文件
        script = script.replaceAll("#sc#", "\n");
        ReFiles.append(script, new File(absolutePath));

        return script;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
