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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author sharuopeng
 *
 */
@Configuration
public class TanyaConfigureAdapter extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addResourceHandler("/tanya/**").addResourceLocations("tanya/");
        super.addResourceHandlers(registry);
    }

}
