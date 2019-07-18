package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.model.wx.AccessToken;
import com.amcjt.jbhim.model.wx.TemplateMsg;
import com.amcjt.jbhim.service.impl.WeChatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/api/wechat")
public class WeChatController extends BaseController {
    @Autowired
    private WeChatService weChatService;

    /**
     * 1 第一步：用户同意授权，获取code
     */
    @GetMapping("authorize")
    public String authorize() throws UnsupportedEncodingException {
        String url = weChatService.authorize();
        return "redirect:" + url;
    }

    /**
     * 第二步：通过code换取网页授权access_token和openId
     */
    @GetMapping("userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        // returnUrl 就是携带openId信息 要跳转到的页面
        AccessToken accessTokenAndOpenId = weChatService.getAccessTokenAndOpenId(code);
        return "redirect:" + returnUrl + "?openId=" + accessTokenAndOpenId.getOpenId();
    }

    /**
     * test
     */
    @GetMapping("sendMsg")
    public String sendMsg(String openId, String templateId) {
        openId = StringUtils.isBlank(openId) ? "oD8hS1QQKvEG5mr1UAoqtBgZ45C0" : openId;
        templateId = StringUtils.isBlank(templateId) ? "JUQP6KkPUDJ-qzMiIRdUBzr-k8OAy7RRJszHX7LYLU0" : templateId;
        TemplateMsg templateMsg = new TemplateMsg();
        templateMsg.setToUser(openId);
        templateMsg.setTemplateId(templateId);
        Map<String, Map<String, String>> map = new TreeMap<>();
        map.put("first", TemplateMsg.item("name", "sdsa"));
        templateMsg.setData(map);
        boolean b = weChatService.sendTemplateMessage(templateMsg);
        return String.valueOf(b);
    }

    /**
     * test
     */
    @GetMapping("callback")
    @ResponseBody
    public String callback(String signature, String timestamp, String nonce, String echostr) {
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        return echostr;
    }

    /**
     * test
     */
    @PostMapping("callback")
    @ResponseBody
    public String callbackPost(HttpServletRequest httpServletRequest) throws IOException {
        System.out.println(httpServletRequest);
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try (ServletInputStream inputStream = httpServletRequest.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        }
        System.out.println(resultBuffer);

        return "success";
    }

}
