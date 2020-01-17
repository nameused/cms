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
import com.github.cms.entity.User;
import com.github.cms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/1/6
 * @company Dingxuan
 */
@RestController
@Api(tags = "UserController",description = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 得到所有用户
     */
    @ApiOperation("获取所有有效用户")
    @GetMapping(value = "/getAlluser", produces = {"application/json;charset=UTF-8"})
    public Object  getAllUser () {

        List<User> allUser = userService.findAllUserByStatus();
        for (User user : allUser) {
            System.out.println(user);
        }
        return new CommonResult().pageSuccess(allUser);
    }

}
