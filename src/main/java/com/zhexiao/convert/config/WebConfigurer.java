package com.zhexiao.convert.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Value("${app.fileDest}")
    private String fileDest;

    @Value("${app.filePathRoute}")
    private String filePathRoute;

    /**
     * addResourceHandler：图片的映射路径
     * addResourceLocations：资源的存储位置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        // yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
        // Properties properties = yamlMapFactoryBean.getObject();
        // String filePathRoute = properties.getProperty("app.filePathRoute", "/uploaderFile/");

        if("".equals(filePathRoute) || null == filePathRoute){
            filePathRoute = "/word/";
        }
        registry.addResourceHandler(filePathRoute+"**").addResourceLocations("file:"+fileDest + "/");
    }
}
