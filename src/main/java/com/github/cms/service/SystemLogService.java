package com.github.cms.service;

import com.github.cms.dao.SystemLogRepository;
import com.github.cms.entity.SystemLog;
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

@Service
public class SystemLogService {
    @Autowired
    SystemLogRepository systemLogRepository;

    public Page<SystemLog> findSysLogListByParam(SystemLog systemLog, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<SystemLog> spec = (Specification<SystemLog>) (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (StringUtils.isNotBlank(systemLog.getOperator())) {
                predicatesList.add(cb.like(root.get("operator"), "%" + systemLog.getOperator().trim() + "%"));
            }
            if (StringUtils.isNotBlank(systemLog.getOperateType())) {
                predicatesList.add(cb.like(root.get("operateType"), "%" + systemLog.getOperateType().trim() + "%"));
            }
            if (StringUtils.isNotBlank(systemLog.getOperateContent())) {
                predicatesList.add(cb.like(root.get("operateContent"), "%" + systemLog.getOperateContent().trim() + "%"));
            }
            query.orderBy(cb.desc(root.get("id")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
        return systemLogRepository.findAll(spec, pageable);
    }
    public void save(SystemLog systemLog){
        systemLogRepository.save(systemLog);
    }

}
