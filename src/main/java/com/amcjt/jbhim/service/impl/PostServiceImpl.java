package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Post;
import com.amcjt.jbhim.repository.jpa.repository.PostRepository;
import com.amcjt.jbhim.service.PostService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<Post> findAll(PaginatedFilter paginatedFilter) {
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize());
        return postRepository.findAll(of);
    }

    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public void delete(String id) {
        postRepository.deleteById(id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
}
