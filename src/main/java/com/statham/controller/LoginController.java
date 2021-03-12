package com.statham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author JIAJUN KOU
 */
@Controller
public class LoginController {

//    @RequestMapping("login")
//    public String login(){
//        System.out.println("登录方法。。。");
//        return "redirect:main.html";
//    }


    @RequestMapping("toMain")
    public String toMain(){

        return "redirect:main.html";
    }
    @RequestMapping("toError")
    public String toError(){

        return "redirect:error.html";
    }
}
