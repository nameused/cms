package com.github.cms.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.cms.controller.RoleController;
import com.github.cms.entity.Device;
import com.github.cms.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DeviceExcelLister extends AnalysisEventListener<Device> {
    private static final Logger logger = LoggerFactory.getLogger(DeviceExcelLister.class);
    public  List<Device> deviceList = new ArrayList<>();
    public DeviceService deviceService;

    public DeviceExcelLister(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void invoke(Device device, AnalysisContext analysisContext) {
        deviceList.add(device);
        //根据业务进行测试
        doSomething(device);
    }

    /**
     * 读取表头内容
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }


    /**
     * 插入数据库
     */
    public void doSomething(Device device) {
        deviceService.saveDevice(device);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("doAfterAllAnalysed----打印-----");
        for (Device device : deviceList) {
            logger.info(device.toString());
            //   deviceService.saveDevice(device);
        }
    }
}
