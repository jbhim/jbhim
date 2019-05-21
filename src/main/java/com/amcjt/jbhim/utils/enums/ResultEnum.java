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
    PARAM_ERROR(1, "参数不正确"),
    /**
     * 用户名错误
     */
    USERNAME_NOT_FOUND(400, "用户名错误"),
    /**
     * 密码错误
     */
    PASSWORD_NOT_FOUND(400, "密码错误"),
    /**
     * 上传失败
     */
    UPLOAD_FAIL(400, "上传失败"),
    /**
     * 找不到数据
     */
    NOT_FOUND(404, "找不到数据");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
