package com.seoul.feedback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://3.34.88.141")
                .allowedMethods("*")
                .maxAge(MAX_AGE_SECS);
    }
}
