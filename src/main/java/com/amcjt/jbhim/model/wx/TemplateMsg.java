package com.amcjt.jbhim.model.wx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class TemplateMsg {
    //接收者openid
    @JSONField(name = "touser")
    private String toUser;
    //模板ID
    @JSONField(name = "template_id")
    private String templateId;
    //模板跳转链接
    private String url;
    //data数据
    private Map<String, Map<String, String>> data;

    /**
     * 参数
     *
     * @param value key
     * @param color 可不填
     */
    public static Map<String, String> item(String value, String color) {
        Map<String, String> params = new TreeMap<>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }

}
