package com.amcjt.jbhim.utils.enums;

import lombok.Getter;

/**
 * @author jbhim
 * @date 2018/12/1/001.
 */
@Getter
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 参数不正确
     */
    PARAM_ERROR(1, "参数不正确");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
