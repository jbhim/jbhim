package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetail extends UserDetailsService {

    /**
     * 保存用户
     *
     * @param account
     */
    void save(Account account);

    ResultVO findAll(PaginatedFilter paginatedFilter);

    ResultVO findAll();

    Account findById(String id);

    void delete(String id);

    void resetPassword(Account account, String currentUserId);

    void enable(Account account);
}
