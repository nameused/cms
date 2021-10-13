package com.github.cms.service;

import com.github.cms.dao.SystemLogRepository;
import com.github.cms.entity.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemLogService {
    @Autowired
    SystemLogRepository systemLogRepository;


    public void save(SystemLog systemLog){
        systemLogRepository.save(systemLog);
    }

}
