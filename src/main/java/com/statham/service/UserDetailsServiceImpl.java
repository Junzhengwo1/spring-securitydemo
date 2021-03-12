package com.statham.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * @author JIAJUN KOU
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.应该是先查一下数据，用户是否存在，
        //这里我们模拟一下数据库
        System.out.println("执行了方法。。。。。。。");

        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //2.存在用户的话，将密码解析（这个密码应该是用户注册时加过密的密码）
        String password = pw.encode("123");

        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
    }
}
