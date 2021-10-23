package com.github.cms.controller;

import com.github.cms.dto.CommonResult;
import com.github.cms.entity.SystemLog;
import com.github.cms.service.SystemLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangmingyang
 * @date 2021/10/19 18:04
 */
@RestController
@RequestMapping("/syslog")
public class SysLogController {
    @Autowired
    SystemLogService systemLogService;
    @ApiOperation("获取系统日志")
    @GetMapping(value = "/getLogList",produces = {"application/json;charset=UTF-8"})
    public Object getSysLog(@RequestParam(required = false) String operator,
                            @RequestParam(required = false) String operateType,
                            @RequestParam(required = false) String operateResult,
                            @RequestParam(required = false) String operateTime,
                            @RequestParam int page,
                            @RequestParam int count){
        SystemLog systemLog=new SystemLog();
        systemLog.setOperator(operator);
        systemLog.setOperateType(operateType);
        systemLog.setOperateResult(operateResult);
       // systemLog.setOperateDate(operateTime);
        Page<SystemLog> systemLogPage=systemLogService.findSysLogListByParam(systemLog,page,count);
        return new CommonResult().pageSuccess(systemLogPage);
    }
}
