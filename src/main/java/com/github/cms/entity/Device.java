/**
 * Copyright DingXuan. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cms.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 * @company Dingxuan
 */
@Entity
@Table(name = "device")
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ExcelProperty(value = "序号", index = 0)
    private Long id;

    @ExcelProperty(value = "设备名称", index = 1)
    private String deviceName;

    @ExcelProperty(value = "设备地址", index = 2)
    private String deviceAddress;

    @ExcelProperty(value = "ROOT密码", index = 3)
    private String rootPassword;

    @ExcelProperty(value = "设备用途", index = 4)
    private String deviceDes;

    @ExcelProperty(value = "创建时间", index = 5)
    private Date createTime;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getRootPassword() {
        return rootPassword;
    }

    public void setRootPassword(String rootPassword) {
        this.rootPassword = rootPassword;
    }

    public String getDeviceDes() {
        return deviceDes;
    }

    public void setDeviceDes(String deviceDes) {
        this.deviceDes = deviceDes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", deviceAddress='" + deviceAddress + '\'' +
                ", rootPassword='" + rootPassword + '\'' +
                ", deviceDes='" + deviceDes + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
