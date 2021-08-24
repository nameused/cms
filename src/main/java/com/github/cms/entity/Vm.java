package com.github.cms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vm")
public class Vm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "vm_ip")
    private String vmIp;
    @Column
    private String vmName;
    @Column
    private String owner;
    @Column
    private String rootPassword;
    @Column
    private Date createTime;
    @Column
    private String startMethod;
    @Column
    private String insidePort;
    @Column
    private String internetPort;
    @Column
    private String url;
    @Column
    private int status;
    @Column
    private String host;
    @Column(name = "note",length = 255)
    private String note;


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
