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

import com.github.cms.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/1/19
 * @company Dingxuan
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    /**
     * 根据角色Id获取权限授权
     * @param roleId
     * @return
     */
    @Query(value = "SELECT p.* FROM role_permission r LEFT JOIN permission p ON p.id  = r.permission_id WHERE r.role_id = ?", nativeQuery = true)
    List<Permission> findPermissionList(@Param("roleId")Long roleId);
}
