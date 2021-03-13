package com.statham;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author dell
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecuritydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecuritydemoApplication.class, args);
    }

}
