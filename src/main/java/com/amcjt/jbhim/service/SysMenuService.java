package com.amcjt.jbhim.service;

import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.repository.jpa.entity.SysMenu;
import com.amcjt.jbhim.utils.PaginatedFilter;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    Map<String, Object> findAllMenu(PaginatedFilter paginatedFilter);

    SysMenu saveMenu(SysMenu sysMenu);

    List<SysMenu> findMenuByLevel(String level);

    SysMenu findById(String id);

    void deleteById(String id);

    List<TreeModel> getMenuTree();

    List<TreeModel> menuByUser(String currentUserId, String currentUserName);
}
