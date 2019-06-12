package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jk.dao")
public class Poitest1Application {

    public static void main(String[] args) {
        SpringApplication.run(Poitest1Application.class, args);
    }

}
