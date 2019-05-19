package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jbhim
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    Page<Task> findAllByTitleContaining(String title, Pageable pageable);

    Page<Task> findAllByTitleContainingAndUserId(String title, String userId, Pageable pageable);
}
