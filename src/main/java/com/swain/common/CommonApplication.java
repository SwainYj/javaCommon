package com.swain.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class CommonApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}
