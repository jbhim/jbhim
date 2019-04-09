package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.service.DocumentService;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController extends BaseController {
    private final DocumentService documentService;

    @Autowired
    public TestController(DocumentService documentService) {
        this.documentService = documentService;
    }

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

    @GetMapping
    public List<TreeModel> getDocDirTree() {
        return documentService.getDocDirTree();
    }
}
