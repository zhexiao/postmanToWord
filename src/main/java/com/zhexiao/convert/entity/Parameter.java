package com.zhexiao.convert.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Data
@Accessors(chain = true)
public class Parameter {
    private String key;
    private String value;
}
