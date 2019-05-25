package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.repository.jpa.entity.Task;
import com.amcjt.jbhim.repository.jpa.repository.TaskRepository;
import com.amcjt.jbhim.service.TaskService;
import com.amcjt.jbhim.service.UserDetail;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * @author jbhim
 * @date 2019/5/19/019.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private UserDetail userDetail;

    @Override
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        String title = paginatedFilter.getFilter("title");
        String userId = paginatedFilter.getFilter("userId");
        String finish = paginatedFilter.getFilter("finish");
        title = title == null ? "" : title;
        Page<Task> taskPage;
        if (StringUtils.isBlank(userId)) {
            if (StringUtils.isBlank(finish)) {
                taskPage = taskRepository.findAllByUserNameContaining(title, of);
            } else {
                taskPage = taskRepository
                        .findAllByUserNameContainingAndIsFinish(title, "true".equals(finish), of);
            }
        } else {
            if (StringUtils.isBlank(finish)) {
                taskPage = taskRepository.findAllByTitleContainingAndUserId(title, userId, of);
            } else {
                taskPage = taskRepository
                        .findAllByTitleContainingAndUserIdAndIsFinish(title, userId, "true".equals(finish), of);
            }

        }
        return ResultVO.success(new HashMap<String, Object>() {{
            put("list", taskPage.getContent());
            put("count", taskPage.getTotalElements());
        }});
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public void save(Task task) {
        Account byId = userDetail.findById(task.getUserId());
        task.setUserName(byId.getName());
        taskRepository.save(task);
    }

    @Override
    public void finish(Task task) {
        Task byId = findById(task.getId());
        byId.setFinish(true);
        byId.setRealEndTime(new Date());
        // 任务分发人和任务归属人才能完成
        if (byId.getUserId().equals(task.getUserId())) {
            taskRepository.save(byId);
        }
    }
}
