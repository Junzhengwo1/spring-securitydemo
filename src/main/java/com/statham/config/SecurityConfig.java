package com.statham.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author JIAJUN KOU
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //当发现是/login时认为时登录，开始验证
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                //登录成功后，挑战到指定页面必须时post请求
                //.successForwardUrl("/toMain")
                //登录成功后的处理器不可以与successForward不能共存
                .successHandler(new MyAuthenticationHandler("/main.html"))
                //.failureForwardUrl("/toError");
                .failureHandler(new MyAuthenticationHandler("http://www.bilibili.com"));

        //授权认证
        http.authorizeRequests()
                //login.html不需要认证
                .antMatchers("/error.html").permitAll()
                .antMatchers("/login.html").permitAll()
                //所有请求都需要被认证
                .antMatchers("/js/**","/images/**").permitAll()
                .anyRequest()
                .authenticated();

        //关闭csrf防护
        http.csrf().disable();


    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

}
