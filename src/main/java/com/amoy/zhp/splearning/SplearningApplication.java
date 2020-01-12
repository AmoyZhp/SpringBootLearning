package com.amoy.zhp.splearning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.amoy.zhp.splearning.mapper")
public class SplearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplearningApplication.class, args);
    }

}
