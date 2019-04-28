package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAllMenu();

    SysMenu saveMenu(SysMenu sysMenu);
}
