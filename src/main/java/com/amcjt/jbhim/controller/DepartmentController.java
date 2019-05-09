package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Department;
import com.amcjt.jbhim.service.DepartmentService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResultVO save(@RequestBody Department department) {
        departmentService.save(department);
        return ResultVO.success();
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Map<String, Object> map = new HashMap<>();
        Page<Department> all = departmentService.findAll(paginatedFilter);
        map.put("list", all.getContent());
        map.put("count", all.getTotalElements());
        return ResultVO.success(map);
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        Department department = departmentService.findById(id);
        return ResultVO.success(department);
    }

    @Delete("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        departmentService.delete(id);
        return ResultVO.success();
    }
}
