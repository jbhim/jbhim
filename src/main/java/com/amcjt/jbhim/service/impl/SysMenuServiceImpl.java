package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.repository.jpa.entity.SysMenu;
import com.amcjt.jbhim.repository.jpa.repository.SysMenuRepository;
import com.amcjt.jbhim.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuRepository menuRepository;

    @Override
    public List<SysMenu> findAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public SysMenu saveMenu(SysMenu sysMenu) {
        return menuRepository.save(sysMenu);
    }


}
