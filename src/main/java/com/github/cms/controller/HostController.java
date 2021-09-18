package com.github.cms.controller;

import com.alibaba.excel.EasyExcel;
import com.github.cms.dto.CommonResult;
import com.github.cms.dto.VmParam;
import com.github.cms.entity.Device;
import com.github.cms.entity.Vm;
import com.github.cms.service.DeviceService;
import com.github.cms.service.FileService;
import com.github.cms.service.VmService;
import com.github.cms.util.CmsConstant;
import com.github.cms.util.DeviceExcelLister;
import com.github.cms.util.FIleUtil;
import com.github.cms.util.VmExcelLister;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


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
    private FileService fileService;
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

    @ApiOperation("根据id删除设备信息")
    @PostMapping(value = "/deleteDevice/{id}")
    @ResponseBody
    public Object deleteDevice(@PathVariable Long id) {
        logger.info("数据Id：" + id);
        Long result = deviceService.deleteDeviceById(id);
        if (result == 1) {
            logger.info("返回数据：" + result);
            return new CommonResult().success("删除设备信息成功！");
        }
        logger.info("返回数据：" + result);
        return new CommonResult().failed("删除设备信息失败！");
    }


    @ApiOperation("下载设备文件")
    @PostMapping("/downloadDeviceFile")
    public ResponseEntity<Resource> downloadDeviceFile(HttpServletRequest request) throws IOException {
        return new FIleUtil().downFileTemplate(fileService, CmsConstant.DEVICE_EXCEL_TEMPLATE, request);
    }


    @ApiOperation("导入设备信息")
    @RequestMapping(value = "/importDeviceExcel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object importDeviceExcel(@RequestParam("file") MultipartFile file) {
        if (Strings.isBlank(fileService.fileUpload(file))) {
            return new CommonResult().failed("文件上传失败！");
        }
        EasyExcel.read(fileService.fileUpload(file), Device.class, new DeviceExcelLister(deviceService)).sheet().doRead();
        return new CommonResult().success("文件导入成功！");
    }


    @ApiOperation("新增虚拟机")
    @RequestMapping(value = "/addVm", method = RequestMethod.POST)
    @ResponseBody
    public Object addVm(@RequestBody VmParam vmParam) {
        Vm vm = new Vm();
        BeanUtils.copyProperties(vmParam, vm);
        Vm data = vmService.saveVm(vm);
        if (data != null) {
            return new CommonResult().success(vm);
        }
        return new CommonResult().failed("新增虚拟机失败！");
    }

    @ApiOperation("下载Vm文件")
    @GetMapping("/downloadVmFile")
    public ResponseEntity<Resource> downloadVmFile(HttpServletRequest request) throws IOException {
        return new FIleUtil().downFileTemplate(fileService, CmsConstant.VM_EXCEL_TEMPLATE, request);
    }

    @ApiOperation("导入Vm信息")
    @PostMapping(value = "/importVmExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object importVmExcel(@RequestParam("file") MultipartFile file) {
        if (Strings.isBlank(fileService.fileUpload(file))) {
            return new CommonResult().failed("文件上传失败！");
        }
        EasyExcel.read(fileService.fileUpload(file), Vm.class, new VmExcelLister(vmService)).sheet().doRead();
        return new CommonResult().success("文件导入成功！");
    }

    @ApiOperation("根据id删除Vm信息")
    @PostMapping(value = "/deleteVm/{id}")
    public Object deleteVm(@PathVariable Long id) {
        Long result = vmService.deleteVm(id);
        if (result == 1) {
            return new CommonResult().success("删除Vm成功！");
        }
        return new CommonResult().failed("删除Vm失败！");
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
