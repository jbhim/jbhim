package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.SysMenu;
import com.amcjt.jbhim.service.SysMenuService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu")
@Slf4j
public class MenuController extends BaseController {
    private final SysMenuService sysMenuService;

    @Autowired
    public MenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }


    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return ResultVO.success(sysMenuService.findAllMenu(paginatedFilter));
    }

    @PostMapping
    public ResultVO save(@RequestBody SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return ResultVO.success();
    }

    @GetMapping("/level/{level}")
    public ResultVO findAllByLevel(@PathVariable("level") String level) {
        return ResultVO.success(sysMenuService.findMenuByLevel(level));
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        return ResultVO.success(sysMenuService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResultVO deleteById(@PathVariable("id") String id) {
        sysMenuService.deleteById(id);
        return ResultVO.success();
    }

    @GetMapping("menuTree")
    public ResultVO getMenuTree() {
        return ResultVO.success(sysMenuService.getMenuTree());
    }

    @GetMapping("menuByUser")
    public ResultVO menuByUser() {
        return ResultVO.success(sysMenuService.menuByUser(getCurrentUserId(), getCurrentUserName()));
    }
}
