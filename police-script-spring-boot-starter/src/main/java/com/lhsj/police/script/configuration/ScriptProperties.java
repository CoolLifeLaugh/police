package com.lhsj.police.script.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.io.ReFiles.isFileExists;

@ConfigurationProperties(prefix = "police.script")
public class ScriptProperties {
    private String       enable;
    private String       app;
    /**
     * 脚本的绝对路径
     */
    private String       absolutePath;
    /**
     * 执行前的校验密码
     */
    private String       password;
    /**
     * 是否暴露上下文
     */
    private Boolean      exposureContext = Boolean.TRUE;
    /**
     * 暴露上下文的name
     */
    private String       contextName     = "context";
    /**
     * 远程调用的脚本，必须匹配host，为空则不校验
     */
    private List<String> hosts;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        isTrue(isFileExists(absolutePath), "absolutePath invalid");
        this.absolutePath = absolutePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Boolean getExposureContext() {
        return exposureContext;
    }

    public void setExposureContext(Boolean exposureContext) {
        this.exposureContext = exposureContext;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
}
