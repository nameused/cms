/**
 * Copyright Dingxuan. All Rights Reserved.
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

package com.github.cms.util;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.cms.entity.Vm;
import com.github.cms.service.VmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhangmingyang
 * @Date: 2021/9/12
 */
public class VmExcelLister extends AnalysisEventListener<Vm> {
    private static final Logger logger = LoggerFactory.getLogger(VmExcelLister.class);

    public VmService vmService;

    public VmExcelLister(VmService vmService) {
        this.vmService=vmService;
    }

    @Override
    public void invoke(Vm vm, AnalysisContext analysisContext) {
        doSomething(vm);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private void doSomething(Vm vm){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);
        logger.info("当前时间："+dateString);
        vm.setCreateTime(date);
        vmService.saveVm(vm);
    }

}
