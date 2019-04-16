package com.amcjt.jbhim.service;

import com.amcjt.jbhim.model.MailBean;

/**
 * @author jbhim
 * @date 2019/4/16/016.
 */
public interface MailService {
    /**
     * 发送一个简单格式的邮件
     *
     * @param mailBean mail实体
     */
    void sendSimpleMail(MailBean mailBean);

    /**
     * 发送一个HTML格式的邮件
     *
     * @param mailBean mail实体
     */
    void sendHTMLMail(MailBean mailBean);

    /**
     * 发送带附件格式的邮件
     *
     * @param mailBean mail实体
     */
    void sendAttachmentMail(MailBean mailBean);

    /**
     * 发送带静态资源的邮件
     *
     * @param mailBean mail实体
     */
    void sendInlineMail(MailBean mailBean);
}
