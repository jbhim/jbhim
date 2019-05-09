package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jbhim
 * @date 2019/5/2/002.
 */

public interface PostRepository extends JpaRepository<Post, String> {

}
