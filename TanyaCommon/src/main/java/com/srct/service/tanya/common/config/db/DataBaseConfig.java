/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.config.db 
 * @author: Sharp   
 * @date: 2019/01/18
 */
package com.srct.service.tanya.common.config.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataBaseConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "db.config.tanya")
	public DataSource TanyaConfigDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

}
