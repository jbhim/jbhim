package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Department;
import com.amcjt.jbhim.utils.PaginatedFilter;
import org.springframework.data.domain.Page;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
public interface DepartmentService {
    Page<Department> findAll(PaginatedFilter paginatedFilter);

    Department findById(String id);

    void delete(String id);

    void save(Department department);
}
