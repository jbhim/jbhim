package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jbhim
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Page<Role> findAllByNameContaining(String title, Pageable pageable);
}
