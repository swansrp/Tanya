/**
 * @Title: TanyaSecurityConfig.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.security
 * @author Sharp
 * @date 2019-01-31 16:11:34
 */
package com.srct.service.tanya.common.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
 */
@Configuration
@EnableWebSecurity
public class TanyaSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AbstractAuthenticationProcessingFilter authenticationProcessingFilter;

    public TanyaSecurityConfig(AuthenticationEntryPoint authenticationEntryPoint,
        AbstractAuthenticationProcessingFilter authenticationProcessingFilter) {
        Log.e("======TanyaSecurityConfig init========");
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationProcessingFilter = authenticationProcessingFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/lib/**", "/webjars/**", "/images/**", "swagger*", "*.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域
        http.csrf().disable();
        
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            // 允许所有人访问 /login
            .authorizeRequests().antMatchers("/login").permitAll()
            // swagger
            .antMatchers("/configuration/**").permitAll().antMatchers("/csrf").permitAll()
            .antMatchers("/swagger*/**").permitAll().antMatchers("/v2/api-docs").permitAll().antMatchers("/error")
            .permitAll()

            // 任意访问请求都必须先通过认证
            .anyRequest().authenticated().and()
            // 启用 iframe 功能
            .headers().frameOptions().disable().and()
            
            
            // 将自定义的 AbstractAuthenticationProcessingFilter 加在 Spring 过滤器链中
            .addFilterBefore(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
