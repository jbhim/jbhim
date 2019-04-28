package com.amcjt.jbhim.service;

import com.amcjt.jbhim.model.MailModel;

/**
 * @author jbhim
 * @date 2019/4/16/016.
 */
public interface MailService {
    /**
     * 发送一个简单格式的邮件
     *
     * @param mailModel mail实体
     */
    void sendSimpleMail(MailModel mailModel);

    /**
     * 发送一个HTML格式的邮件
     *
     * @param mailModel mail实体
     */
    void sendHTMLMail(MailModel mailModel);

    /**
     * 发送带附件格式的邮件
     *
     * @param mailModel mail实体
     */
    void sendAttachmentMail(MailModel mailModel);

    /**
     * 发送带静态资源的邮件
     *
     * @param mailModel mail实体
     */
    void sendInlineMail(MailModel mailModel);
}
