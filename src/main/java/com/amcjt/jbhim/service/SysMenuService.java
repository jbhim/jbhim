package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.SysMenuEntity;

import java.util.List;

public interface SysMenuService {
    List<SysMenuEntity> findAllMenu();

    SysMenuEntity saveMenu(SysMenuEntity sysMenuEntity);
}
