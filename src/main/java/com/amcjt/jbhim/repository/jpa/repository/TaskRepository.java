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

    Page<Task> findAllByUserNameContaining(String title, Pageable pageable);

    Page<Task> findAllByUserNameContainingAndIsFinish(String title, boolean finish, Pageable pageable);

    Page<Task> findAllByTitleContainingAndUserId(String title, String userId, Pageable pageable);

    Page<Task> findAllByTitleContainingAndUserIdAndIsFinish(String title, String userId, boolean finish, Pageable pageable);
}
