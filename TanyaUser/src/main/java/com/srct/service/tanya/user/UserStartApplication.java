/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.user
 * @author: srct
 * @date: 2019/01/30
 */
package com.srct.service.tanya.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: UserStartApplication
 * @Description: Start Application for Tanya - User
 */

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
// @EnableTransactionManagement
public class UserStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserStartApplication.class, args);
    }
}