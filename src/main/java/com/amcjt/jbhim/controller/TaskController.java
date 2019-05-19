package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Task;
import com.amcjt.jbhim.service.TaskService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("task")
@Slf4j
public class TaskController extends BaseController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return taskService.findAll(paginatedFilter);
    }

    @PostMapping
    public ResultVO save(@RequestBody Task task) {
        if (StringUtils.isBlank(task.getId())) {
            task.setCreateUserId(getCurrentUserId());
            task.setCreateUserName(getCurrentName());
        }
        taskService.save(task);
        return ResultVO.success();
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable String id) {
        taskService.findById(id);
        return ResultVO.success();
    }

    @GetMapping("getMyTask")
    public ResultVO getMyTask(PaginatedFilter paginatedFilter) {
        paginatedFilter.setFilter("userId", getCurrentUserId());
        taskService.findAll(paginatedFilter);
        return ResultVO.success();
    }

    @PostMapping("finish")
    public ResultVO finish(@RequestBody Task task) {
        task.setUserId(getCurrentUserId());
        taskService.finish(task);
        return ResultVO.success();
    }
}
