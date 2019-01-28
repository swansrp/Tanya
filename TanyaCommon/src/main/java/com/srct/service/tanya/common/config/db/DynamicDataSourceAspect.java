/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.config.db 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.common.config.db;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.srct.service.config.db.DataSourceContextHolder;

@Aspect
@Component
public class DynamicDataSourceAspect {

	@Pointcut("execution(* com.srct.service.tanya.common.datalayer.tanya.repository..*.*(..))")
	public void repositoryTanya() {
        // Just a pointCut function
    }

	@Before("repositoryTanya()")
	public void beforeTanya() {
		DataSourceContextHolder.setDB("tanya");
    }

	@After("repositoryTanya()")
	public void afterTanya() {
        DataSourceContextHolder.clearDB();
    }

}
