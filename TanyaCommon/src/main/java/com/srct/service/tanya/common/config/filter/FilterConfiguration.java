/**
 * Title: FilterConfiguration.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.filter
 * @author Sharp
 * @date 2019-02-09 00:15:58
 */
package com.srct.service.tanya.common.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author Sharp
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean filterDemo4Registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(roleFilter());
        registration.addUrlPatterns("/*");
        // 拦截规则
        registration.addInitParameter("exclusions",
                "/portal/admin/*," + "/login,/logout,/register,/test/*,/reset,/qrcode,"
                        + "/swagger-ui.html,/swagger-resources,/swagger-resources/*,/v2/api-docs,"
                        + "/webjars/springfox-swagger-ui/*,/configuration/*,/public/*,/csrf,"
                        + "/css/*,/js/*,/img/*,/images/*,/druid/*,/Captcha.jpg");
        // 拦截规则
        registration.addInitParameter("roleRequired", "/role/*," + "/goods/*,/order/*,/discount/*,/campaign/*,/shop/*");
        // 过滤器名称
        registration.setName("RoleFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "sessionFilter")
    public Filter roleFilter() {
        return new RoleFilter();
    }

}
