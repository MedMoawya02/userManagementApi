package com.usermanagement.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.usermanagement.controller",
        "com.usermanagement.exception"
})
public class WebConfig implements WebMvcConfigurer {
}