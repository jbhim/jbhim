package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Role;
import com.amcjt.jbhim.utils.PaginatedFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {
    Map<String, Object> findAll(PaginatedFilter paginatedFilter);

    List<Role> findAll();

    Role saveRole(Role role);

    Role findById(String id);

    void deleteById(String id);

    Set<String> getRoleMember(String roleId);

    void saveRoleMember(String roleId, List<String> list);

    void removeRoleMember(String roleId, List<String> list);

    void saveRoleMenu(String roleId, List<String> list);

    Set<String> roleHasMenu(String roleId);
}
