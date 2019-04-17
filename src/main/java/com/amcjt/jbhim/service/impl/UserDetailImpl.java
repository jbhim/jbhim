package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.model.AccountModel;
import com.amcjt.jbhim.service.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserDetailImpl implements UserDetail {

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        AccountModel accountModel = new AccountModel();
        accountModel.setId("1");
        accountModel.setName("admin");
        accountModel.setJobNum("1");
        accountModel.setPhone("123456");
        accountModel.setEmail("7777");
        accountModel.setUsername(userName);
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        accountModel.setPassword(encode);
        accountModel.setEnabled(true);
        return accountModel;
    }
}
