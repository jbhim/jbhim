package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.enums.ResultEnum;
import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController extends BaseController {

    @RequestMapping("getRoot")
    public Object fun() {
        return getRootPath();
    }

    @RequestMapping("ex")
    public ResultVO test(String a, String b) {
        log.info("【ex】 参数, b={}", a);
        if (!"jbhim".equals(a)) {
            log.error("【ex】 参数不正确, a={}, b={}", a, b);
            throw new JbhimException(ResultEnum.PARAM_ERROR);

        }
        return ResultVO.success(getRootPath());
    }
}
