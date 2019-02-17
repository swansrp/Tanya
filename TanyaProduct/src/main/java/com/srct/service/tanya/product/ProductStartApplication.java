/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.product
 * @author: Administrator
 * @date: 2019/02/17
 */
package com.srct.service.tanya.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: ProductStartApplication
 * @Description: Start Application for Tanya - Product
 */

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class ProductStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductStartApplication.class, args);
    }
}