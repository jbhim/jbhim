package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.RoleMember;
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
public interface RoleMemberRepository extends JpaRepository<RoleMember, String> {

    List<RoleMember> findAllByRoleId(String roleId);

    List<RoleMember> findAllByUserId(String userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from role_member where roleId =:roleId and userId = :userId")
    void deleteRoleMembersByRoleIdAndUserId(@Param("roleId") String roleId, @Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from role_member where roleId =:roleId")
    void deleteRoleMembersByRoleId(@Param("roleId") String roleId);
}
