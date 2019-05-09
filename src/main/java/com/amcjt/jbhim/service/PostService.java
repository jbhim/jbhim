package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Post;
import com.amcjt.jbhim.utils.PaginatedFilter;
import org.springframework.data.domain.Page;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */
public interface PostService {
    Page<Post> findAll(PaginatedFilter paginatedFilter);

    Post findById(String id);

    void delete(String id);

    void save(Post post);
}
