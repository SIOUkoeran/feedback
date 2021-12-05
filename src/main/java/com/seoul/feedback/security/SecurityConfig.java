package com.seoul.feedback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**", "/h2/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                    .logoutSuccessUrl("/").permitAll()
                    .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/api/v1/login", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService)

        ;


    }
}
