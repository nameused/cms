package com.github.cms.dao;

import com.github.cms.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VmRepository extends JpaSpecificationExecutor<Device>, JpaRepository<Device,Long> {
}
