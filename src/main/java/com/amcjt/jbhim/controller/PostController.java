package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.repository.jpa.entity.Post;
import com.amcjt.jbhim.service.PostService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResultVO save(@RequestBody Post post) {
        postService.save(post);
        return ResultVO.success();
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Map<String, Object> map = new HashMap<>();
        Page<Post> all = postService.findAll(paginatedFilter);
        map.put("list", all.getContent());
        map.put("count", all.getTotalElements());
        return ResultVO.success(map);
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        Post post = postService.findById(id);
        return ResultVO.success(post);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        postService.delete(id);
        return ResultVO.success();
    }

    @GetMapping("findAll")
    public ResultVO findAll() {
        return ResultVO.success(postService.findAll());
    }
}
