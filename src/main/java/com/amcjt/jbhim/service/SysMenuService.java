package com.amcjt.jbhim.service;

import com.amcjt.jbhim.entity.SysMenuEntity;

import java.util.List;

public interface SysMenuService {
    List<SysMenuEntity> findAllMenu();

    SysMenuEntity saveMenu(SysMenuEntity sysMenuEntity);
}
