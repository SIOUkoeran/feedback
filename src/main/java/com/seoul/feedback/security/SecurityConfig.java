package com.seoul.feedback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**", "/h2/**", "/h2-console/**", "/docs/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                    .logoutSuccessUrl("/").permitAll()
                    .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                ;


    }


}
