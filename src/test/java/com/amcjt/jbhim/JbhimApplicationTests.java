package com.amcjt.jbhim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JbhimApplicationTests {

    @Test
    public void contextLoads() {
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fun() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.apiopen.top/novelSearchApi?name={1}";
        Map map = restTemplate.getForObject(url, Map.class, "锦衣夜行");
        assert map != null;
        map.forEach((key, value) -> System.out.println(key + "        " + value));
    }

}
