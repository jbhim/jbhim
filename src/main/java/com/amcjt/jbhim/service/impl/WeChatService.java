package com.amcjt.jbhim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amcjt.jbhim.config.WeChatConfig;
import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.model.wx.AccessToken;
import com.amcjt.jbhim.model.wx.TemplateMsg;
import com.amcjt.jbhim.utils.HttpRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class WeChatService {
    /**
     * 获取code
     * APPID, REDIRECT_URI,SCOPE,STATE
     */
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s"
            + "&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * 获取网页授权的access_token,没有次数限制
     * APPID, SECRET,CODE
     */
    private static final String ACCESS_TOKEN_URL_FOR_WEB_VIEW = "https://api.weixin.qq.com/sns/oauth2/access_token?"
            + "appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 获取基础access_token,有次数限制
     * APPID, APPSECRET
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token"
            + "?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 发送模板消息接口
     * APPID, SECRET,CODE
     */
    private static final String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message"
            + "/template/send?access_token=%s";
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String authorize() throws UnsupportedEncodingException {
        String mpAppId = weChatConfig.getMpAppId();
        String redirectUrl = weChatConfig.getCodeRedirectUrl();
        String bindOpenIdPageUrl = weChatConfig.getBindOpenIdPage();
        return String.format(AUTHORIZE_URL, mpAppId, URLEncoder.encode(redirectUrl, "utf-8"), "snsapi_userinfo", bindOpenIdPageUrl);
    }

    /**
     * 通过code获取网页授权的access_token(非基础access_token); 网页授权的access_token是没有次数限制的
     */
    public AccessToken getAccessTokenAndOpenId(String code) {
        String exchangeOpenIdUrl = String.format(ACCESS_TOKEN_URL_FOR_WEB_VIEW, weChatConfig.getMpAppId(), weChatConfig.getMpAppSecret(), code);
        String jsonString = restTemplate.getForObject(exchangeOpenIdUrl, String.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(jsonObject.getString("access_token"));
        accessToken.setExpiresIn(jsonObject.getString("expires_in"));
        accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
        accessToken.setOpenId(jsonObject.getString("openid"));
        accessToken.setScope(jsonObject.getString("scope"));
        return accessToken;
    }

    /**
     * 发送模板消息
     */
    public boolean sendTemplateMessage(TemplateMsg templateMsg) {
        String accessToken = getAccessToken();
        String sendTemplateMsgUrl = String.format(SEND_TEMPLATE_MSG_URL, accessToken);
        String jsonString = JSONObject.toJSONString(templateMsg);
        String response = HttpRequestHelper.sendPost(sendTemplateMsgUrl, jsonString, "utf-8", "3");
        if (response.contains("errcode")) {
            JSONObject jsonObject = JSONObject.parseObject(response);
            int errCode = jsonObject.getIntValue("errcode");
            String errMsg = jsonObject.getString("errmsg");
            throw new JbhimException(errCode, errMsg);
        }
        return response.contains("ok");
    }

    /**
     * 获取基础access_token, 这个token有次数限制, 要保存
     */
    public String getAccessToken() {
        // 先从 redis 中获取 数据
        String accessToken = getAccessTokenFromRedis();
        if (accessToken == null) {
            accessToken = getAccessTokenFromWx();
        }
        return accessToken;
    }

    /**
     * 从redis 中取token
     */
    private String getAccessTokenFromRedis() {
        return redisTemplate.opsForValue().get("access_token");
    }

    /**
     * 微信申请token
     */
    private String getAccessTokenFromWx() {
        String accessTokenUrl = String.format(ACCESS_TOKEN_URL, weChatConfig.getMpAppId(), weChatConfig.getMpAppSecret());
        String jsonString = restTemplate.getForObject(accessTokenUrl, String.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String accessToken;
        if (jsonString != null && jsonString.contains("errcode")) {
            throw new JbhimException(jsonString);
        } else {
            accessToken = jsonObject.getString("access_token");
            long expiresIn = jsonObject.getLong("expires_in");
            //存到redis, 并设置过期时间
            redisTemplate.opsForValue().set("access_token", accessToken, expiresIn - 200, TimeUnit.SECONDS);
        }
        return accessToken;
    }

    public boolean checkSignature() {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String[] arrTmp = new String[]{weChatConfig.getToken(), timestamp, nonce};
        Arrays.sort(arrTmp);
        String strTmp = StringUtils.join(arrTmp, "");
        strTmp = DigestUtils.sha1Hex(strTmp).toLowerCase();
        if (strTmp.equals(signature)) {
            return true;
        }
        return false;
    }

    public String pushInfo() {
        return null;
    }
}
