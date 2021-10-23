package com.github.cms.dao;

import com.github.cms.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SystemLogRepository extends JpaSpecificationExecutor<SystemLog>,JpaRepository<SystemLog,Long> {

}
