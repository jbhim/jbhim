package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Task;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;

/**
 * @author jbhim
 * @date 2019/5/19/019.
 */
public interface TaskService {
    ResultVO findAll(PaginatedFilter paginatedFilter);

    Task findById(String id);

    void save(Task task);

    void finish(Task task);
}
