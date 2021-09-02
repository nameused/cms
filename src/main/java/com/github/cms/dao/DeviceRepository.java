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
package com.github.cms.dao;

import com.github.cms.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhangmingyang
 * @Date: 2020/11/23
 * @company Dingxuan
 */
@Repository
public interface DeviceRepository extends JpaSpecificationExecutor<Device>, JpaRepository<Device,Long> {
    /**
     * 根据主键ID删除用户信息
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Long deleteDeviceById(Long id);
}
