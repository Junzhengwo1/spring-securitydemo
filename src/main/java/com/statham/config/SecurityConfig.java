package com.statham.config;

import com.statham.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.management.PersistentMBean;
import javax.sql.DataSource;

/**
 * @author JIAJUN KOU
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //当发现是/login时认为时登录，开始验证
                .loginProcessingUrl("/login")
                .loginPage("/showLogin")
                //登录成功后，挑战到指定页面必须时post请求
                .successForwardUrl("/toMain")

                //登录成功后的处理器不可以与successForward不能共存
                //.successHandler(new MyAuthenticationHandler("/main.html"))
                .failureForwardUrl("/toError");
                //.failureHandler(new MyAuthenticationHandler("http://www.bilibili.com"));

        //授权认证
        http.authorizeRequests()
                //login.html不需要认证
                //.antMatchers("/error.html").permitAll()
                .antMatchers("/error.html").access("permitAll()")
                //.antMatchers("/login.html").permitAll()
                .antMatchers("/showLogin").permitAll()
                //所有请求都需要被认证
                .antMatchers("/js/**","/images/**").permitAll()
                //.antMatchers("/main1.html").hasAuthority("admin")
                //.antMatchers("main1.html").hasRole("abc")
                .anyRequest()
                .authenticated();
                //.anyRequest()
                //.access("@myServiceImpl.hasPermission(request,authentication)");

        //关闭csrf防护
        //http.csrf().disable();
        //记住我功能
        http.rememberMe()
                //设置记住我操作的失效时间
                .tokenValiditySeconds(60)
                //自定义的登录逻辑
                .userDetailsService(userDetailsService)
                //持久层对象
                .tokenRepository(persistentTokenRepository);

        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        http.logout()
                //自定义的注销登录
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html");

    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
