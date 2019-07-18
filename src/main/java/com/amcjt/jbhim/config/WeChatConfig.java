package com.amcjt.jbhim.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WeChatConfig {
    /**
     * 公众号appId
     */
    @Value("${wechat.mp.appId}")
    private String mpAppId;
    /**
     * 公众号appSecret
     */
    @Value("${wechat.mp.appSecret}")
    private String mpAppSecret;
    /**
     * 任务转移模板id
     */
    @Value("${wechat.mp.templateId.taskTransfer}")
    private String taskTransferTemplateId;
    /**
     * 登记页面路由
     */
    @Value("${wechat.bindOpenIdPage}")
    private String bindOpenIdPage;
    /**
     * 微信获取code 回调地址(需要在微信开发者中心配置域名)
     */
    @Value("${wechat.codeRedirectUrl}")
    private String codeRedirectUrl;
}
