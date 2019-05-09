package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Department;
import com.amcjt.jbhim.repository.jpa.repository.DepartmentRepository;
import com.amcjt.jbhim.service.DepartmentService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public Page<Department> findAll(PaginatedFilter paginatedFilter) {
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize());
        return departmentRepository.findAll(of);
    }

    @Override
    public Department findById(String id) {
        return departmentRepository.findById(id).orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }


    @Override
    public void delete(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public void save(Department department) {
        //todo 逻辑判断校验
        departmentRepository.save(department);
    }
}
