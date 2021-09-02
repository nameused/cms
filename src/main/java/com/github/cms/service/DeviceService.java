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
package com.github.cms.service;

import com.github.cms.dao.DeviceRepository;
import com.github.cms.entity.Device;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 * @company Dingxuan
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;


    public Page<Device> findDeviceListByParam(Device device, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Device> spec = (Specification<Device>) (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (StringUtils.isNotBlank(device.getDeviceName())) {
                predicatesList.add(cb.like(root.get("deviceName"), "%" + device.getDeviceName().trim() + "%"));
            }
            if (StringUtils.isNotBlank(device.getDeviceAddress())) {
                predicatesList.add(cb.like(root.get("deviceAddress"), "%" + device.getDeviceAddress().trim() + "%"));
            }
            if (StringUtils.isNotBlank(device.getDeviceDes())) {
                predicatesList.add(cb.like(root.get("deviceDes"), "%" + device.getDeviceDes().trim() + "%"));
            }
            query.orderBy(cb.desc(root.get("id")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
        return deviceRepository.findAll(spec, pageable);
    }

    public Long deleteDeviceById(Long id){
       return deviceRepository.deleteDeviceById(id);
    }


    public Device saveDevice(Device device) {
        deviceRepository.save(device);
        return device;
    }

}
