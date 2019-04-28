package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.service.UserDetail;
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
 * @date 2019/4/27/027.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserDetail userDetail;

    @Autowired
    public UserController(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @PostMapping
    public ResultVO save(@RequestBody Account account) {
        userDetail.save(account);
        return ResultVO.success();
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Map<String, Object> map = new HashMap<>();
        Page<Account> all = userDetail.findAll(paginatedFilter);
        map.put("list", all.getContent());
        map.put("count", all.getTotalElements());
        return ResultVO.success(map);
    }

    @GetMapping("/{id}")
    public ResultVO findAll(@PathVariable("id") String id) {
        Account account = userDetail.findById(id);
        return ResultVO.success(account);
    }

    @Delete("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        userDetail.delete(id);
        return ResultVO.success();
    }
}
