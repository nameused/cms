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

import com.github.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangmingyang
 * @Date: 2020/1/6
 * @company Dingxuan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据活动状态获取所有用户
     *
     * @param integer
     * @return
     */
    List<User> findAllUserByStatus(Integer integer);

    /**
     * 通过用户名获取用户
     *
     * @param userName
     * @return
     */
    User findUserByUsername(String userName);


    /**
     * 查询相同用户名的总数
     *
     * @param userName
     * @return
     */
    Long countByUsername(String userName);

    /**
     * 根据主键ID删除用户信息
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Long deleteUserById(Long id);

    /**
     * 更新用户状态
     *
     * @param id
     * @param status
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update user set status = ?2 where id= ?1", nativeQuery = true)
    Integer updateUserStatus(Long id, Integer status);
}
