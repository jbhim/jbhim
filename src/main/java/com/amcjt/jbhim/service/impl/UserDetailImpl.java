package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.model.AccountModel;
import com.amcjt.jbhim.service.UserDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImpl implements UserDetail {
    @Override
    public AccountModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        return null;
    }
}
