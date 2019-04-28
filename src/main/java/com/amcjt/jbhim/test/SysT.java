package com.amcjt.jbhim.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jbhim
 * @date 2019/4/13/013.
 */
@Component
public class SysT implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        //System.getenv().forEach((key, value) -> System.out.println(key + ": " + value));
        //System.getProperties().forEach((key, value) -> System.out.println(key + ": " + value));
        Instant now = Instant.now();
        List<String> conn = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {

            String s = RandomStringUtils.randomNumeric(6);
            //System.out.println(s);
            conn.add(s);
        }
        long count = conn.parallelStream().filter(i -> "362083".equals(i)).count();
        System.out.println(count);
        Duration between = Duration.between(now, Instant.now());
        LocalDateTime now1 = LocalDateTime.now();
        int hour = now1.getHour();
        int second = now1.getSecond();
        int minute = now1.getMinute();
        System.out.println("" + hour + minute + second);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            System.out.println("hello word");
        }
    }
}
