package com.amcjt.jbhim.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController extends BaseController {

    @RequestMapping("getRoot")
    public Object fun() {
        return getRootPath();
    }
}
