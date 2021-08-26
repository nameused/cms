package com.github.cms.controller;

import com.alibaba.excel.EasyExcel;
import com.github.cms.dto.CommonResult;
import com.github.cms.dto.VmParam;
import com.github.cms.entity.Device;
import com.github.cms.entity.Vm;
import com.github.cms.service.DeviceService;
import com.github.cms.service.FileService;
import com.github.cms.service.VmService;
import com.github.cms.util.DeviceExcelLister;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 */
@RestController
@Api(tags = "HostController", description = "主机管理")
@RequestMapping("/host")
public class HostController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private FileService fileUploadService;
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

    @ApiOperation("新增虚拟机")
    @RequestMapping(value = "/addVm", method = RequestMethod.POST)
    @ResponseBody
    public Object addVm(@RequestBody VmParam vmParam) {
        Vm vm = vmService.saveVm(vmParam);
        if (vm != null) {
            return new CommonResult().success(vm);
        }
        return new CommonResult().failed("新增虚拟机失败");
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


    @ApiOperation("导入设备信息")
    @RequestMapping(value = "/importDeviceExcel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object importDeviceExcel(@RequestParam("file") MultipartFile file) {
        if (Strings.isBlank(fileUploadService.fileUpload(file))) {
            return new CommonResult().failed("文件上传失败");
        }
        EasyExcel.read(fileUploadService.fileUpload(file), Device.class, new DeviceExcelLister()).sheet().doRead();
        for (Device device : DeviceExcelLister.deviceList) {
            deviceService.saveDevice(device);
        }
        return new CommonResult().success("文件导入成功！");
    }

//    @ApiOperation("导入设备信息")
//    @RequestMapping(value = "/importDeviceExcel", method = RequestMethod.POST)
//    @ResponseBody
//    public void importDevice() {
//        EasyExcel.read("D:\\test\\aus\\device.xlsx", Device.class, new DeviceExcelLister()).sheet().doRead();
//    }
}
