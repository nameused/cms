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
package com.github.cms.controller;

import com.github.cms.dto.CommonResult;
import com.github.cms.entity.Role;
import com.github.cms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/1/21
 * @company Dingxuan
 */
@RestController
@Api(tags = "RoleController", description = "角色管理")
@RequestMapping("/sys/role")
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;

    /**
     * 得到所有已激活用户
     */
    @ApiOperation("获取所有激活用户")
    @GetMapping(value = "/getAllRoleList", produces = {"application/json;charset=UTF-8"})
    public Object findAllRoleList() {
        List<Role> allRoleList = roleService.findAllRoleList();
        if (allRoleList.size() > 0) {
            return new CommonResult().pageSuccess(allRoleList);
        }
        return new CommonResult().failed("获取角色失败");
    }

}
