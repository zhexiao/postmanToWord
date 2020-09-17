package com.zhexiao.convert.entity.postman;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Data
@Accessors(chain = true)
public class Response {
    private String name;

    private String body;
}
