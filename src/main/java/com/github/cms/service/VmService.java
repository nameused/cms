package com.github.cms.service;

import com.github.cms.dao.VmRepository;
import com.github.cms.dto.VmParam;
import com.github.cms.entity.Vm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class VmService {
    @Autowired
    private VmRepository vmRepository;


    public Page<Vm> findAllVmList(Vm vm, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Vm> spec = (Specification<Vm>) (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (StringUtils.isNotBlank(vm.getVmName())) {
                predicatesList.add(cb.like(root.get("vmName"), "%" + vm.getVmName().trim() + "%"));
            }
            if (StringUtils.isNotBlank(vm.getVmIp())) {
                predicatesList.add(cb.like(root.get("vmIp"), "%" + vm.getVmIp().trim() + "%"));
            }
            if (StringUtils.isNotBlank(vm.getOwner())) {
                predicatesList.add(cb.like(root.get("owner"), "%" + vm.getOwner().trim() + "%"));
            }
            query.orderBy(cb.asc(root.get("createTime")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
        return vmRepository.findAll(spec, pageable);
    }

    public Vm saveVm(VmParam vmParam) {
        Vm vm = new Vm();
        BeanUtils.copyProperties(vmParam, vm);
        vmRepository.save(vm);
        return vm;
    }
}
