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

import com.github.cms.dao.UserRepository;
import com.github.cms.dto.UserParam;
import com.github.cms.entity.User;
import com.github.cms.util.CmsConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author zhangmingyang
 * @Date: 2020/1/6
 * @company Dingxuan
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<User> findAllUserByStatus() {
        return userRepository.findAllUserByStatus(User.STATUS);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public long countByUserName(String userName){
        return userRepository.countByUserName(userName);
    }
    public User saveUser(UserParam userParam){
        System.out.println(userParam);
        User user=new User();
        BeanUtils.copyProperties(userParam,user);
        System.out.println(user);
        user.setCreateTime(new Date());
        user.setStatus(CmsConstant.USER_ACTIVE_STATUS);
        String md5Password = passwordEncoder.encode(user.getPassword());
        user.setPassword(md5Password);
        System.out.println(user);
        userRepository.save(user);
        return user;
    }

    public Long deleteUserById(Long id){
       return userRepository.deleteUserById(id);
    }

    public Integer updateUserStatus(Long id,Integer status){
        return userRepository.updateUserStatus(id,status);
    }
}
