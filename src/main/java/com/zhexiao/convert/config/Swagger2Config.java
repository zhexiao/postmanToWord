package com.zhexiao.convert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 *
 * Swagger2API文档的配置
 *
 * @author zhexiao
 * @date 2020-06-25 9:35
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //配置文档信息
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "接口文档生成器", // 标题
                "根据数据生成标准的word接口文档", // 描述
                "v1.0", // 版本
                "", // 组织链接
                null, // 联系人信息
                "GPL 3.0", // 许可
                "", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
        //默认返回所有接口
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhexiao.convert.controller"))
                .build()
                .groupName("API_V1");
    }
}