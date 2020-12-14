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

import com.github.cms.dao.HostRepository;
import com.github.cms.entity.Host;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
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
public class HostService {
    @Autowired
    private HostRepository hostRepository;


    public Page<Host> findHostListByParam( Host host, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Host> spec = (Specification<Host>) (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (StringUtils.isNotBlank(host.getHostName())) {
                predicatesList.add(cb.like(root.get("hostName"), "%" + host.getHostName().trim() + "%"));
            }
            if (StringUtils.isNotBlank(host.getHostAddress())) {
                predicatesList.add(cb.like(root.get("hostAddress"), "%" + host.getHostAddress().trim() + "%"));
            }
            if (StringUtils.isNotBlank(host.getHostDes())) {
                predicatesList.add(cb.like(root.get("hostDes"), "%" + host.getHostDes().trim() + "%"));
            }
            query.orderBy(cb.asc(root.get("createTime")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
        return hostRepository.findAll(spec,pageable);
    }

}
