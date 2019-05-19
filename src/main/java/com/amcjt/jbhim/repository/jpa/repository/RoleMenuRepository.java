package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jbhim
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, String> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from role_menu where roleId =:roleId")
    void deleteRoleMenuByRoleId(@Param("roleId") String roleId);

    List<RoleMenu> findAllByRoleId(String roleId);

    List<RoleMenu> findAllByRoleIdIn(Iterable<String> roleIds);
}
