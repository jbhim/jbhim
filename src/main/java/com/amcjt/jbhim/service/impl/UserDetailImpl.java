package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.model.AccountModel;
import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.repository.jpa.repository.AccountRepository;
import com.amcjt.jbhim.repository.jpa.repository.DepartmentRepository;
import com.amcjt.jbhim.repository.jpa.repository.PostRepository;
import com.amcjt.jbhim.service.UserDetail;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.UpdateTool;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class UserDetailImpl implements UserDetail {

    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailImpl(AccountRepository accountRepository, DepartmentRepository departmentRepository, PostRepository postRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.postRepository = postRepository;
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
            } else {
                account.setPassword(passwordEncoder.encode("123456"));
            }
        } else {
            Account source = findById(account.getId());
            UpdateTool.copyNullProperties(source, account);
        }
        account.setEnabled(true);
        Account save = accountRepository.save(account);
        if (save == null) {
            log.info("【{}】 保存用户失败", this.getClass().getSimpleName());
            throw new JbhimException(400, "保存失败");
        }
    }

    @Override
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
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
            pageList = accountRepository.findAllByNameContaining(name, of);
        } else {
            pageList = accountRepository.findAllByNameContainingOrJobNumEquals(name, jobNum, of);
        }

        List<Account> newPageList = pageList.getContent().stream().peek(account -> {
            if (account.getDepId() != null) {
                departmentRepository.findById(account.getDepId())
                        .ifPresent(department -> account.setDepName(department.getDepName()));
            }
            if (account.getPostId() != null) {
                postRepository.findById(account.getPostId())
                        .ifPresent(post -> account.setPostName(post.getPostName()));
            }
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("list", newPageList);
        map.put("count", pageList.getTotalElements());
        return ResultVO.success(map);
    }

    @Override
    public ResultVO findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "jobNum");
        return ResultVO.success(accountRepository.findAll(sort));
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
