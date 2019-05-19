package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.model.AccountModel;
import com.amcjt.jbhim.repository.jpa.entity.Account;
import com.amcjt.jbhim.service.UserDetail;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jbhim
 * @date 2019/4/27/027.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserDetail userDetail;
    private final ConsumerTokenServices consumerTokenServices;

    @Autowired
    public UserController(UserDetail userDetail, ConsumerTokenServices consumerTokenServices) {
        this.userDetail = userDetail;
        this.consumerTokenServices = consumerTokenServices;
    }

    @PostMapping
    public ResultVO save(@RequestBody Account account) {
        userDetail.save(account);
        return ResultVO.success();
    }

    @GetMapping
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        return userDetail.findAll(paginatedFilter);
    }

    @GetMapping("findAll")
    public ResultVO findAll() {
        return userDetail.findAll();
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable("id") String id) {
        Account account = userDetail.findById(id);
        return ResultVO.success(account);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        userDetail.delete(id);
        return ResultVO.success();
    }

    @GetMapping("getUserInfo")
    public AccountModel getUserInfo(OAuth2Authentication user) {
        return (AccountModel) user.getPrincipal();
    }

    /**
     * 删除认证token接口 可与logout替换
     */
    @DeleteMapping(value = "/tokens/revoke")
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && StringUtils.containsIgnoreCase(authorization, "Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            consumerTokenServices.revokeToken(tokenId);
        }
    }
}
