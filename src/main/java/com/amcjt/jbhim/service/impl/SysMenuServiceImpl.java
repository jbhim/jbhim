package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.repository.jpa.entity.SysMenuEntity;
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
    public List<SysMenuEntity> findAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public SysMenuEntity saveMenu(SysMenuEntity sysMenuEntity) {
        return menuRepository.save(sysMenuEntity);
    }


}
