package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.model.MailModel;
import com.amcjt.jbhim.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author jbhim
 * @date 2019/4/16/016.
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Value("${jbhim.mail.sender}")
    private String mailSender;

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendSimpleMail(MailModel mailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom(mailSender);
        //邮件接收人
        simpleMailMessage.setTo(mailModel.getRecipient());
        //邮件主题
        simpleMailMessage.setSubject(mailModel.getSubject());
        //邮件内容
        simpleMailMessage.setText(mailModel.getContent());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendHTMLMail(MailModel mailModel) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(mailModel.getRecipient());
            mimeMessageHelper.setSubject(mailModel.getSubject());
            mimeMessageHelper.setText(mailModel.getHtmlContent(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            log.error("sendHTMLMail: ", e);
        }

    }

    @Override
    public void sendAttachmentMail(MailModel mailModel) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(mailModel.getRecipient());
            mimeMessageHelper.setSubject(mailModel.getSubject());
            mimeMessageHelper.setText(mailModel.getContent());
            //文件路径
            Map<String, Object> attachment = mailModel.getAttachment();
            if (attachment != null && attachment.size() > 0) {
                for (Map.Entry<String, Object> entry : attachment.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof File) {
                        File file = (File) value;
                        mimeMessageHelper.addAttachment(key, file);
                    } else if (value instanceof InputStreamResource) {
                        InputStreamResource inputStreamResource = (InputStreamResource) value;
                        mimeMessageHelper.addAttachment(key, inputStreamResource);
                    }
                }
            } else {
                throw new MessagingException("附加不能为空");
            }

            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }

    @Override
    public void sendInlineMail(MailModel mailModel) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(mailModel.getRecipient());
            mimeMessageHelper.setSubject(mailModel.getSubject());
            mimeMessageHelper.setText("<html><body>带静态资源的邮件内容，这个一张IDEA配置的照片:<img src='cid:picture' /></body></html>", true);
            //文件路径
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
            mimeMessageHelper.addInline("picture", file);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }
}
