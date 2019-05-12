package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.model.AccountModel;
import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.repository.jpa.repository.AccountRepository;
import com.amcjt.jbhim.service.UserDetail;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.UpdateTool;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class UserDetailImpl implements UserDetail {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailImpl(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsernameOrPhone(userName, userName);
        AccountModel accountModel = new AccountModel();
        Account account = accountOptional.orElseThrow(() -> new JbhimException(ResultEnum.USERNAME_NOT_FOUND));
        BeanUtils.copyProperties(account, accountModel);
        return accountModel;
    }

    @Override
    public void save(Account account) {
        if (StringUtils.isBlank(account.getId())) {
            String maxJobNum = accountRepository.getMaxJobNum();
            String pattern = "000000";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String jobNum = maxJobNum == null ? pattern : decimalFormat.format(Double.parseDouble(maxJobNum));
            account.setJobNum(jobNum);
            if (account.getPassword() != null) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            }
        } else {
            Account source = findById(account.getId());
            UpdateTool.copyNullProperties(source, account);
        }
        Account save = accountRepository.save(account);
        if (save == null) {
            log.info("【{}】 保存用户失败", this.getClass().getSimpleName());
            throw new JbhimException(400, "保存失败");
        }
    }

    @Override
    public Page<Account> findAll(PaginatedFilter paginatedFilter) {
        String name = paginatedFilter.getFilter("name");
        String jobNum = paginatedFilter.getFilter("jobNum");
        Sort sort = Sort.by(Sort.Direction.ASC, "jobNum");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        Page<Account> pageList;
        if (StringUtils.isAllBlank(name, jobNum)) {
            pageList = accountRepository.findAll(of);
        } else if (StringUtils.isNotBlank(jobNum) && StringUtils.isBlank(name)) {
            pageList = accountRepository.findAllByJobNumEquals(jobNum, of);
        } else if (StringUtils.isNotBlank(name) && StringUtils.isBlank(jobNum)) {
            pageList = accountRepository.findAllByNameContaining(jobNum, of);
        } else {
            pageList = accountRepository.findAllByNameContainingOrJobNumEquals(name, jobNum, of);
        }
        return pageList;
    }

    @Override
    public Account findById(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (!account.isPresent()) {
            throw new JbhimException(ResultEnum.NOT_FOUND);
        }
        return account.get();
    }

    @Override
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

}
