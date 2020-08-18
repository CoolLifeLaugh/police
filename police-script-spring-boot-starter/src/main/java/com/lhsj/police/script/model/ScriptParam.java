package com.lhsj.police.script.model;

import com.lhsj.police.core.base.ReObjects;
import com.lhsj.police.core.constant.Constants;
import com.lhsj.police.core.net.ReNets;
import com.lhsj.police.core.text.ReEncodes;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.constant.Constants.EMPTY;
import static com.lhsj.police.core.number.ReNumbers.isNumber;
import static com.lhsj.police.web.request.ReRequests.getParameter;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings("unused")
public class ScriptParam implements Serializable {

    private String  password;
    private Boolean remote;
    private String  host;
    private Integer port;
    private String  script;
    private String  encode;

    public ScriptParam() {
    }

    public static ScriptParam of() {
        return new ScriptParam();
    }

    public static ScriptParam of(HttpServletRequest request) {
        String password = getParameter(request, "password", EMPTY);
        String host = getParameter(request, "host", "127.0.0.1");
        String port = getParameter(request, "port", "8080");
        String script = getParameter(request, "script", EMPTY);
        String encode = getParameter(request, "encode", "false");

        if (StringUtils.equals(encode, "true")) {
            script = new String(ReEncodes.decodeBase64UrlSafe(script));
        }

        isTrue(isNotBlank(password), "password blank");
        isTrue(isNotBlank(script), "script blank");
        isTrue(isNumber(port), "port invalid");

        return ScriptParam.of()
                .password(password)
                .remote(isRemote(host))
                .host(host)
                .port(Integer.parseInt(port))
                .script(script)
                .encode(encode);
    }

    public ScriptParam password(String password) {
        this.password = password;
        return this;
    }

    public ScriptParam remote(Boolean remote) {
        this.remote = remote;
        return this;
    }

    public ScriptParam host(String host) {
        this.host = host;
        return this;
    }

    public ScriptParam port(Integer port) {
        this.port = port;
        return this;
    }

    public ScriptParam script(String script) {
        this.script = script;
        return this;
    }

    public ScriptParam encode(String encode) {
        this.encode = encode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getRemote() {
        return ofNullable(remote).orElse(false);
    }

    public String getHost() {
        return host;
    }

    public String getScript() {
        return script;
    }

    public String getEncode() {
        return encode;
    }

    public Integer getPort() {
        return port;
    }

    private static boolean isRemote(String host) {
        if (Constants.LOCALHOSTS.contains(host)) {
            return false;
        }

        return !ReObjects.equals(host, ReNets.getLocalHost());
    }
}
