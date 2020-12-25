package com.zhexiao.convert.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhe.xiao
 * @date 2020-10-29
 * @description
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    AppConfig appConfig;

    /**
     * addResourceHandler：图片的映射路径
     * addResourceLocations：资源的存储位置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(appConfig.getFilePathRoute() + "**")
                .addResourceLocations("file:" + appConfig.getFileDest() + "/");
    }
}
