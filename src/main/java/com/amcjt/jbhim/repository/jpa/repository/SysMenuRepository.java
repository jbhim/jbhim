package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.SysMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jbhim
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, String> {

    Page<SysMenu> findAllByTitleContaining(String title, Pageable pageable);

    List<SysMenu> findAllByLevel(String level);
}
