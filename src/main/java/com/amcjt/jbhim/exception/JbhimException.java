package com.amcjt.jbhim.exception;

import com.amcjt.jbhim.utils.enums.ResultEnum;
import lombok.Getter;

/**
 * @author jbhim
 * @date 2018/12/1/001.
 */
@Getter
public class JbhimException extends RuntimeException {
    private Integer code;

    public JbhimException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public JbhimException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();

    }
}
