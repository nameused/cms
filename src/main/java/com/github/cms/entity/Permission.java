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

import org.apache.ibatis.annotations.One;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangmingyang
 * @Date: 2020/1/17
 * @company Dingxuan
 */
@Entity
@Table(name = "permission")
public class Permission implements Serializable {
    /**
     * 自增ID
     */
    @Id
    private Long id;
    /**
     * 父ID
     */
    private Long pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 实际值
     */
    private String value;
    /**
     * 图标
     */
    private String icon;
    /**
     * uri 地址
     */
    private String uri;
    /**
     * 类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer type;
    /**
     * 状态 启用状态；0->禁用；1->启用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 排序
     */
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", icon='" + icon + '\'' +
                ", uri='" + uri + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", sort=" + sort +
                '}';
    }
}
