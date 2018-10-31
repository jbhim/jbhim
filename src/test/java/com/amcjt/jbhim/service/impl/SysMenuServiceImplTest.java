package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.entity.SysMenuEntity;
import com.amcjt.jbhim.service.SysMenuService;
import com.amcjt.jbhim.utils.UUIDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceImplTest {
    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void findAllMenu() {
        List<SysMenuEntity> allMenu = sysMenuService.findAllMenu();
        allMenu.forEach(System.out::println);
    }

    @Test
    public void saveMenu() {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        sysMenuEntity.setId(UUIDGenerator.sequentialUUIDString());
        sysMenuEntity.setTitle("主页");
        sysMenuEntity.setUrl("http://www.amcjt.com");
        sysMenuEntity.setParentId("0");
        SysMenuEntity saveMenu = sysMenuService.saveMenu(sysMenuEntity);
        System.out.println(saveMenu);


    }
}