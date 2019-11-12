package com.f.mml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("com.f.mml.extend.dao")
public class MmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmlApplication.class, args);
    }

}
