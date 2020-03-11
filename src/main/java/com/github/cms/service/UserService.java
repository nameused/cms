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
import com.github.cms.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public List<User> findAllUserByStatus() {
        return userRepository.findAllUserByStatus(User.STATUS);
    }

    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    public long countByUserName(String userName) {
        return userRepository.countByUsername(userName);
    }

    public User saveUser(UserParam userParam) {
        System.out.println(userParam);
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        System.out.println(user);
        user.setCreateTime(new Date());
        user.setStatus(CmsConstant.USER_ACTIVE_STATUS);
        String md5Password = passwordEncoder.encode(user.getPassword());
        user.setPassword(md5Password);
        System.out.println(user);
        userRepository.save(user);
        return user;
    }

    public Long deleteUserById(Long id) {
        return userRepository.deleteUserById(id);
    }

    public Integer updateUserStatus(Long id, Integer status) {
        return userRepository.updateUserStatus(id, status);
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    public String login(String userName, String password) {
        String token = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        System.out.println(userDetails.getPassword());
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }
}
