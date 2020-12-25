package com.zhexiao.convert.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件里面 app 下面的配置信息
 *
 * @author zhe.xiao
 * @date 2020-10-29
 * @description
 */
@ConfigurationProperties(prefix = "app")
@Component
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

    private String fileDest = "./";

    private String filePathRoute = "/uploads/";

    private String fileVisitHost = "http://localhost";
}
