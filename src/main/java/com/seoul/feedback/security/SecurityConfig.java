package com.seoul.feedback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/v1/login", "/api/v1/user/login","/access-denied").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .expressionHandler(cinfigExpressionHandler());
        http.exceptionHandling()
                        .authenticationEntryPoint(configAuthenticationEntryPoint())
                                .accessDeniedHandler(configAccessDeniendHandler());
        
        http.oauth2Login()
                .userInfoEndpoint().userService(CustomOauth2UserService)
                .and()
                .successHandler(configSuccessHandler())
                .failureHandler(configFailureHandler())
                .permitAll();
        
        http.httpBasic();
        
        http.logout()
                .deleteCookies("JSESSIONID");
    }

    private AccessDeniedHandler configAccessDeniendHandler() {
    }

    private AuthenticationEntryPoint configAuthenticationEntryPoint() {
    }

    private AuthenticationFailureHandler configFailureHandler() {
    }

    private AuthenticationSuccessHandler configSuccessHandler() {
    }

    private SecurityExpressionHandler<FilterInvocation> cinfigExpressionHandler() {
    }
}
