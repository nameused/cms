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
import com.github.cms.entity.Host;
import com.github.cms.service.HostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 * @company Dingxuan
 */
@RestController
@Api(tags = "HostController", description = "设备管理")
@RequestMapping("/dev/host")
public class HostController {
    @Autowired
    private HostService hostService;
    /**
     * 得到所有已激活用户
     */
    @ApiOperation("获取所有主机信息")
    @GetMapping(value = "/getAllHost", produces = {"application/json;charset=UTF-8"})
    public Object getAllHost() {
        List<Host> allHost =hostService.findAllHostList();
        return new CommonResult().pageSuccess(allHost);
    }
}
