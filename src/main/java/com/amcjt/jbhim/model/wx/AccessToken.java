package com.amcjt.jbhim.model.wx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AccessToken {
    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "expires_in")
    private String expiresIn;
    @JSONField(name = "refresh_token")
    private String refreshToken;
    @JSONField(name = "openid")
    private String openId;
    private String scope;
}
