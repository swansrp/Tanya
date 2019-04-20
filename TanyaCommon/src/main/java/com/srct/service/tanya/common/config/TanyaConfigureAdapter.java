/**
 * Title: TanyaConfigureAdapter.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config
 * @author sharuopeng
 * @date 2019-02-24 17:11:11
 */
package com.srct.service.tanya.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sharuopeng
 */
@Configuration
public class TanyaConfigureAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addResourceHandler("/tanya/**").addResourceLocations("classpath:/tanya/");
    }
}
