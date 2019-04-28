package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jbhim
 */
public interface AccountRepository extends JpaRepository<Account, String> {
    Page<Account> findAllByNameContainingOrJobNumEquals(String name, String jobNum, Pageable pageable);

    Page<Account> findAllByNameContaining(String name, Pageable pageable);

    Page<Account> findAllByJobNumEquals(String jobNum, Pageable pageable);

    Optional<Account> findByUsernameOrPhone(String username, String phone);
}
