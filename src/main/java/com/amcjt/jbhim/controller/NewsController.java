package com.amcjt.jbhim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author jbhim
 * @date 2019/5/20/020.
 */
@RestController
@RequestMapping("other")
public class NewsController {
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("news")
    private Map getNews() {
        Map news = restTemplate.getForObject("http://v.juhe.cn/toutiao/index?type={0}&key={1}",
                Map.class,
                "keji",
                "849ef49fa5c4cadd61200d0ce0d50da8");
        return news;
    }

}
