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
import com.github.cms.dto.UserParam;
import com.github.cms.entity.Permission;
import com.github.cms.entity.User;
import com.github.cms.service.PermissionService;
import com.github.cms.service.UserService;
import com.github.cms.util.CmsConstant;
import com.github.cms.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 *
 * @author zhangmingyang
 * @Date: 2020/1/6
 * @company Dingxuan
 */
@RestController
@Api(tags = "UserController", description = "用户管理")
@RequestMapping("/sys/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 得到所有已激活用户
     */
    @ApiOperation("获取所有激活用户")
    @GetMapping(value = "/getActiveUser", produces = {"application/json;charset=UTF-8"})
    public Object getAllUser() {
        List<User> allUser = userService.findAllUserByStatus();
        for (User user : allUser) {
            System.out.println(user);
        }
        return new CommonResult().pageSuccess(allUser);
    }

    /**
     * 获得用户所有权限
     */
    @ApiOperation("获取用户所有权限")
    @RequestMapping(value = "/permission/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getPermissionList(@PathVariable Long roleId) {
        LOGGER.info("roleId：" + roleId);
        List<Permission> permissionList = permissionService.findPermissionList(roleId);
        return new CommonResult().success(permissionList);
    }

    /**
     * 注册用户
     */
    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody UserParam userParam, Long roleId) {
        LOGGER.info("roleId：" + roleId);
        if (userService.countByUserName(userParam.getUserName()) > 0) {
            return new CommonResult().failed("该用户已注册!");
        }
        User user = userService.saveUser(userParam);
        return new CommonResult().success(user);
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        Long count = userService.deleteUserById(id);
        System.out.println(count);
        return new CommonResult().success("用户删除成功");
    }

    @ApiOperation("更新用户状态")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updataUserStatus(@RequestParam Long id, @RequestParam Integer status) {
        Integer count = null;
        if (CmsConstant.USER_ACTIVE_STATUS == status) {
            count = userService.updateUserStatus(id, CmsConstant.USER_INACTIVE_STATUS);
        } else {
            count = userService.updateUserStatus(id, CmsConstant.USER_ACTIVE_STATUS);
        }
        if (count == 1) {
            return new CommonResult().success("用户状态更新成功");
        }
        System.out.println(count);
        return new CommonResult().failed("用户状态更新失败");
    }

}
