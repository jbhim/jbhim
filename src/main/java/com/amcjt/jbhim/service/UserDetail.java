package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.utils.PaginatedFilter;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetail extends UserDetailsService {

    /**
     * 保存用户
     *
     * @param account
     */
    void save(Account account);

    Page<Account> findAll(PaginatedFilter paginatedFilter);

    Account findById(String id);

    void delete(String id);
}
