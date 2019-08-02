/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.role
 * @author: srct
 * @date: 2019/01/29
 */
package com.srct.service.tanya.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: RoleStartApplication
 * @Description: Start Application for Tanya - Role
 */

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
// @EnableTransactionManagement
public class RoleStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleStartApplication.class, args);
    }
}
