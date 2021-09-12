package com.github.cms.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vm")
public class Vm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelProperty(value = "序号", index = 0)
    private Long id;

    @Column
    @ExcelProperty(value = "名称", index = 1)
    private String vmName;


    @Column
    @ExcelProperty(value = "IP地址", index = 2)
    private String vmIp;

    @Column
    @ExcelProperty(value = "ROOT密码", index = 3)
    private String rootPassword;

    @Column
    @ExcelProperty(value = "负责人", index = 4)
    private String owner;

    @Column
    @ExcelProperty(value = "内网端口", index = 5)
    private String insidePort;

    @Column
    @ExcelProperty(value = "外网端口", index = 6)
    private String outsidePort;

    @Column(columnDefinition = "VARCHAR(32) COMMENT '内网访问地址'")
    @ExcelProperty(value = "内网访问地址", index = 7)
    private String insideUrl;

    @Column(columnDefinition = "VARCHAR(32) COMMENT '外网访问地址'")
    @ExcelProperty(value = "外网访问地址", index = 8)
    private String outsideUrl;

    @Column
    @ExcelProperty(value = "服务启动方式", index = 9)
    private String startMethod;

    @Column(columnDefinition = "varchar(5) COMMENT '服务启用状态'")
    @ExcelProperty(value = "启用状态", index = 10)
    private String startStatus;

    @Column(columnDefinition = "varchar(5) COMMENT '是否为设备'")
    @ExcelProperty(value = "是否为设备", index = 11)
    private String isDevice;


    @Column
    @ExcelProperty(value = "宿主机IP", index = 12)
    private String hostIp;


    @Column(name = "note")
    @ExcelProperty(value = "备注", index = 13)
    private String note;

    @Column
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartMethod() {
        return startMethod;
    }

    public void setStartMethod(String startMethod) {
        this.startMethod = startMethod;
    }

    public String getInsidePort() {
        return insidePort;
    }

    public void setInsidePort(String insidePort) {
        this.insidePort = insidePort;
    }

    public String getOutsidePort() {
        return outsidePort;
    }

    public void setOutsidePort(String outsidePort) {
        this.outsidePort = outsidePort;
    }

    public String getInsideUrl() {
        return insideUrl;
    }

    public void setInsideUrl(String insideUrl) {
        this.insideUrl = insideUrl;
    }

    public String getOutsideUrl() {
        return outsideUrl;
    }

    public void setOutsideUrl(String outsideUrl) {
        this.outsideUrl = outsideUrl;
    }

    public String getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(String isDevice) {
        this.isDevice = isDevice;
    }

    public String getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(String startStatus) {
        this.startStatus = startStatus;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
