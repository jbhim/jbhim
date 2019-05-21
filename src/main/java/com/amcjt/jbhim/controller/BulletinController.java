package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.service.impl.BulletinService;
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
@RequestMapping("/bulletin")
@Slf4j
public class BulletinController {
    @Resource
    private BulletinService bulletinService;

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return bulletinService.findAll(paginatedFilter);
    }

    @PostMapping
    public ResultVO findAll(@RequestBody Bulletin bulletin) {
        bulletinService.save(bulletin);
        return ResultVO.success();
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        bulletinService.delete(id);
        return ResultVO.success();
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        bulletinService.findById(id);
        return ResultVO.success();
    }

    /**
     * 发布下架
     *
     * @param id
     */
    @GetMapping("/publish")
    public ResultVO publish(@PathVariable("id") String id) {
        bulletinService.publish(id);
        return ResultVO.success();
    }
}
