/**
 * Title: CronStartApplication
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 19:26
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron
 */
package com.srct.service.tanya.cron;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class CronStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CronStartApplication.class, args);
    }
}
