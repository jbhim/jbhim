package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Role;
import com.amcjt.jbhim.service.RoleService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("role")
@Slf4j
public class RoleController extends BaseController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return ResultVO.success(roleService.findAll(paginatedFilter));
    }

    @GetMapping("findAll")
    public ResultVO findAll() {
        return ResultVO.success(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        return ResultVO.success(roleService.findById(id));
    }

    @PostMapping
    public ResultVO findById(@RequestBody Role role) {
        roleService.saveRole(role);
        return ResultVO.success();
    }

    @DeleteMapping("/{id}")
    public ResultVO deleteById(@PathVariable("id") String id) {
        roleService.deleteById(id);
        return ResultVO.success();
    }

    @GetMapping("/member/{roleId}")
    public ResultVO getRoleMember(@PathVariable("roleId") String roleId) {
        Set<String> ids = roleService.getRoleMember(roleId);
        return ResultVO.success(ids);
    }

    @PostMapping("/member/{roleId}")
    public ResultVO saveRoleMember(@PathVariable("roleId") String roleId,
                                   @RequestBody List<String> list) {
        roleService.saveRoleMember(roleId, list);
        return ResultVO.success();
    }

    @PostMapping("/deleteMember/{roleId}")
    public ResultVO removeRoleMember(@PathVariable("roleId") String roleId,
                                     @RequestBody List<String> list) {
        roleService.removeRoleMember(roleId, list);
        return ResultVO.success();
    }

    @PostMapping("/roleMenu/{roleId}")
    public ResultVO saveRoleMenu(@PathVariable("roleId") String roleId,
                                 @RequestBody List<String> list) {
        roleService.saveRoleMenu(roleId, list);
        return ResultVO.success();
    }

    @GetMapping("/roleHasMenu/{roleId}")
    public ResultVO roleHasMenu(@PathVariable("roleId") String roleId) {
        return ResultVO.success(roleService.roleHasMenu(roleId));
    }

}
