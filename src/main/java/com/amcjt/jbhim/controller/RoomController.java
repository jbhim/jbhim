package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Room;
import com.amcjt.jbhim.service.RoomService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {
    @Resource
    private RoomService roomService;

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return roomService.findAll(paginatedFilter);
    }

    @PostMapping
    public ResultVO findAll(@RequestBody Room room) {
        roomService.save(room);
        return ResultVO.success();
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        roomService.delete(id);
        return ResultVO.success();
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        roomService.findById(id);
        return ResultVO.success();
    }

}
