package com.github.cms.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class VmParam {
    @ApiModelProperty(value = "虚拟机IP", required = true)
    @NotEmpty(message = "虚拟机IP不能为空")
    private String vmIp;
    @ApiModelProperty(value = "虚拟机名称", required = true)
    private String vmName;
    @ApiModelProperty(value = "负责人", required = true)
    private String owner;
    @ApiModelProperty(value = "root密码", required = true)
    private String rootPassword;
    @ApiModelProperty(value = "启动方式", required = true)
    private String startMethod;
    @ApiModelProperty(value = "内网端口", required = true)
    private String insidePort;
    @ApiModelProperty(value = "互联网端口", required = true)
    private String internetPort;
    @ApiModelProperty(value = "服务访问地址", required = true)
    private String url;

    public String getVmIp() {
        return vmIp;
    }

    public void setVmIp(String vmIp) {
        this.vmIp = vmIp;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRootPassword() {
        return rootPassword;
    }

    public void setRootPassword(String rootPassword) {
        this.rootPassword = rootPassword;
    }

    public String getStartMethod() {
        return startMethod;
    }

    public String getInsidePort() {
        return insidePort;
    }

    public void setInsidePort(String insidePort) {
        this.insidePort = insidePort;
    }

    public String getInternetPort() {
        return internetPort;
    }

    public void setInternetPort(String internetPort) {
        this.internetPort = internetPort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStartMethod(String startMethod) {
        this.startMethod = startMethod;
    }
}
