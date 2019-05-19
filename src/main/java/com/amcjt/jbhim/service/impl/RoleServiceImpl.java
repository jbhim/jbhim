package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Role;
import com.amcjt.jbhim.repository.jpa.entity.RoleMember;
import com.amcjt.jbhim.repository.jpa.entity.RoleMenu;
import com.amcjt.jbhim.repository.jpa.repository.RoleMemberRepository;
import com.amcjt.jbhim.repository.jpa.repository.RoleMenuRepository;
import com.amcjt.jbhim.repository.jpa.repository.RoleRepository;
import com.amcjt.jbhim.service.RoleService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jbhim
 * @date 2019/5/18/018.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    public RoleRepository roleRepository;
    @Resource
    public RoleMenuRepository roleMenuRepository;
    @Resource
    public RoleMemberRepository roleMemberRepository;

    @Override
    public Map<String, Object> findAll(PaginatedFilter paginatedFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        String title = paginatedFilter.getFilter("name");
        title = title == null ? "" : title;
        Page<Role> roles = roleRepository.findAllByNameContaining(title, of);
        return new HashMap<String, Object>() {{
            put("list", roles.getContent());
            put("count", roles.getTotalElements());
        }};
    }

    @Override
    public List<Role> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return roleRepository.findAll(sort);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public void deleteById(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Set<String> getRoleMember(String roleId) {
        return roleMemberRepository.findAllByRoleId(roleId)
                .stream()
                .map(RoleMember::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public void saveRoleMember(String roleId, List<String> list) {
        if (StringUtils.isBlank(roleId)) {
            throw new JbhimException(ResultEnum.PARAM_ERROR);
        }
        List<RoleMember> roleMembers = new ArrayList<>();
        list.forEach(userId -> {
            RoleMember roleMember = new RoleMember();
            roleMember.setRoleId(roleId);
            roleMember.setUserId(userId);
            roleMembers.add(roleMember);
        });
        roleMemberRepository.saveAll(roleMembers);
    }

    @Override
    public void removeRoleMember(String roleId, List<String> list) {
        if (StringUtils.isBlank(roleId)) {
            throw new JbhimException(ResultEnum.PARAM_ERROR);
        }
        list.forEach(userId -> {
            roleMemberRepository.deleteRoleMembersByRoleIdAndUserId(roleId, userId);
        });
    }

    @Override
    public void saveRoleMenu(String roleId, List<String> list) {
        if (StringUtils.isBlank(roleId)) {
            throw new JbhimException(ResultEnum.PARAM_ERROR);
        }
        //保存前删除之前的菜单
        roleMenuRepository.deleteRoleMenuByRoleId(roleId);
        //构建保存集合
        List<RoleMenu> roleMenus = new ArrayList<>();
        list.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });
        roleMenuRepository.saveAll(roleMenus);
    }

    @Override
    public Set<String> roleHasMenu(String roleId) {
        return roleMenuRepository.findAllByRoleId(roleId)
                .stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toSet());
    }
}
