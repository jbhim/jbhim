package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountModel, String> {
}
