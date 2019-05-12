package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.model.AccountModel;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Data
public class BaseController {
    private String rootPath;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Cookie[] cookies;
    private ServletContext servletContext;


    @ModelAttribute
    public void mounting(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.cookies = request.getCookies();
        this.servletContext = session.getServletContext();
        this.rootPath = session.getServletContext().getRealPath("/files");
    }

    public AccountModel getCurrentUserInfo() {
        return (AccountModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public String getCurrentUserId() {
        return getCurrentUserInfo().getId();
    }

    public String getCurrentUserName() {
        return getCurrentUserInfo().getUsername();
    }

    public String getCurrentName() {
        return getCurrentUserInfo().getName();
    }
}
