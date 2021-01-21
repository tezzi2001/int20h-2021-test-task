package com.int20h2021.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TesttaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesttaskApplication.class, args);
    }

}
