package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.repository.jpa.entity.RoleMember;
import com.amcjt.jbhim.repository.jpa.entity.RoleMenu;
import com.amcjt.jbhim.repository.jpa.entity.SysMenu;
import com.amcjt.jbhim.repository.jpa.repository.RoleMemberRepository;
import com.amcjt.jbhim.repository.jpa.repository.RoleMenuRepository;
import com.amcjt.jbhim.repository.jpa.repository.SysMenuRepository;
import com.amcjt.jbhim.service.SysMenuService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.TreeUtil;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuRepository menuRepository;
    @Resource
    private RoleMemberRepository roleMemberRepository;
    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Override
    public Map<String, Object> findAllMenu(PaginatedFilter paginatedFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        String title = paginatedFilter.getFilter("title");
        title = title == null ? "" : title;
        Page<SysMenu> menus = menuRepository.findAllByTitleContaining(title, of);
        return new HashMap<String, Object>() {{
            put("list", menus.getContent());
            put("count", menus.getTotalElements());
        }};
    }

    @Override
    public SysMenu saveMenu(SysMenu sysMenu) {
        return menuRepository.save(sysMenu);
    }

    @Override
    public List<SysMenu> findMenuByLevel(String level) {
        return menuRepository.findAllByLevel(level);
    }

    @Override
    public SysMenu findById(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public void deleteById(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<TreeModel> getMenuTree() {
        List<SysMenu> all = menuRepository.findAll();
        return TreeUtil.buildTree(convertToTreeModel(all));
    }

    @Override
    public List<TreeModel> menuByUser(String currentUserId) {
        //获取角色
        Set<String> roles = roleMemberRepository.findAllByUserId(currentUserId).stream()
                .map(RoleMember::getRoleId).collect(Collectors.toSet());

        //获取有权限的菜单
        Set<String> menuId = roleMenuRepository.findAllByRoleIdIn(roles).stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toSet());

        List<SysMenu> allById = menuRepository.findAllById(menuId);
        return TreeUtil.buildTree(convertToTreeModel(allById));
    }

    private List<TreeModel> convertToTreeModel(List<SysMenu> allById) {
        List<TreeModel> treeModelList = new ArrayList<>();
        allById.forEach(sysMenu -> {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(sysMenu.getId());
            treeModel.setParentId(sysMenu.getParentId());
            treeModel.setTitle(sysMenu.getTitle());
            treeModelList.add(treeModel);
        });
        return treeModelList;
    }

}
