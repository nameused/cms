package com.github.cms.dao;

import com.github.cms.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLog,Long> {
}
