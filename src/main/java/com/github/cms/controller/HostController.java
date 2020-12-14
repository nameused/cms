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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 * @company Dingxuan
 */
@RestController
@Api(tags = "HostController", description = "设备管理")
@RequestMapping("/dev/host")
public class HostController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private HostService hostService;

    /**
     * 得到所有已激活用户
     */
    @ApiOperation("获取所有主机信息")
    @GetMapping(value = "/getAllHost", produces = {"application/json;charset=UTF-8"})
    public Object getAllHost(@RequestParam(required = false) String hostName,
                             @RequestParam(required = false) String hostAddress,
                             @RequestParam(required = false) String createTime,
                             @RequestParam(required = false) String hostDes,
                             @RequestParam int page,
                             @RequestParam int count) {
        Host host = new Host();
        host.setHostName(hostName);
        host.setHostAddress(hostAddress);
        host.setHostDes(hostDes);
        logger.info("创建时间："+createTime);

       // host.setCreateTime(hostDes);
        Page<Host> allHost = hostService.findHostListByParam(host,page,count);
        return new CommonResult().pageSuccess(allHost);
    }
}
