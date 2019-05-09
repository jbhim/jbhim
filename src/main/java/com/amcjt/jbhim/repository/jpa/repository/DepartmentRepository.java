package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
}
