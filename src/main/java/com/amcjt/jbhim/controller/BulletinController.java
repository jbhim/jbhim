package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.service.BulletinService;
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
public class BulletinController extends BaseController {
    @Resource
    private BulletinService bulletinService;

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return bulletinService.findAll(paginatedFilter);
    }

    @PostMapping
    public ResultVO findAll(@RequestBody Bulletin bulletin) {
        bulletin.setUserId(getCurrentUserId());
        bulletin.setUserName(getCurrentUserName());
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
        return ResultVO.success(bulletinService.findById(id));
    }

    /**
     * 发布下架
     *
     * @param bulletin
     */
    @PostMapping("/publish")
    public ResultVO publish(@RequestBody Bulletin bulletin) {
        bulletinService.publish(bulletin.getId());
        return ResultVO.success();
    }

    @GetMapping("/show")
    public ResultVO show() {
        return bulletinService.show();
    }
}
