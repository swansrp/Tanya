/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: PortalStartApplication 
 * @Description: Start Application for Tanya - Portal 
 */

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
// @EnableTransactionManagement
public class PortalStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalStartApplication.class, args);
    }
}