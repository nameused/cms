package com.github.cms.dao;

import com.github.cms.entity.Vm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VmRepository extends JpaSpecificationExecutor<Vm>, JpaRepository<Vm,Long> {

}
