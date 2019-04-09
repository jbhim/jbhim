package com.amcjt.jbhim.exception;

import com.amcjt.jbhim.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jbhim
 * @date 2018/12/1/001.
 */
@RestControllerAdvice
public class JbhimExceptionHandler {
    /**
     * 处理全局的自定义异常
     *
     * @param e JbhimException
     * @return ResultVO
     */
    @ExceptionHandler(value = JbhimException.class)
    public ResultVO exceptionHandler(JbhimException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }

}
