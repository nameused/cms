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
import com.github.cms.entity.Device;
import com.github.cms.entity.Vm;
import com.github.cms.service.DeviceService;
import com.github.cms.service.VmService;
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
@Api(tags = "HostController", description = "主机管理")
@RequestMapping("/host")
public class HostController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private VmService vmService;

    /**
     * 得到所有已激活用户
     */
    @ApiOperation("获取硬件设备信息")
    @GetMapping(value = "/getDevList", produces = {"application/json;charset=UTF-8"})
    public Object getDevInfo(@RequestParam(required = false) String deviceName,
                             @RequestParam(required = false) String deviceAddress,
                             @RequestParam(required = false) String createTime,
                             @RequestParam(required = false) String deviceDes,
                             @RequestParam int page,
                             @RequestParam int count) {
        Device device = new Device();
        device.setDeviceName(deviceName);
        device.setDeviceAddress(deviceAddress);
        device.setDeviceDes(deviceDes);
        logger.info("创建时间：" + createTime);
        Page<Device> deviceList = deviceService.findDeviceListByParam(device, page, count);
        return new CommonResult().pageSuccess(deviceList);
    }

    @ApiOperation("获取虚拟机服务信息")
    @GetMapping(value = "/getVmList", produces = {"application/json;charset=UTF-8"})
    public Object getVmList(@RequestParam(required = false) String vmName,
                            @RequestParam(required = false) String vmIp,
                            @RequestParam(required = false) String owner,
                            @RequestParam(required = false) String createTime,
                            @RequestParam int page,
                            @RequestParam int count) {
        Vm vm = new Vm();
        vm.setVmName(vmName);
        vm.setVmIp(vmIp);
        vm.setOwner(owner);
        Page<Vm> vmList = vmService.findAllVmList(vm, page, count);
        return new CommonResult().pageSuccess(vmList);
    }

}
