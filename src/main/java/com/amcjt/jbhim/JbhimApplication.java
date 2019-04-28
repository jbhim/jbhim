package com.amcjt.jbhim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MapperScan("com.amcjt.jbhim.repository.mybatis.mapper")
@SpringBootApplication
@EnableJpaAuditing
public class JbhimApplication {

    public static void main(String[] args) {
        SpringApplication.run(JbhimApplication.class, args);
    }
}
