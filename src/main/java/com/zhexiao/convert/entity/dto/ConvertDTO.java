package com.zhexiao.convert.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/17
 * @Description
 */
@Data
@Accessors(chain = true)
public class ConvertDTO {
    /**
     * 调用系统
     */
    private String callSystem;

    /**
     * 数据格式
     */
    private String dataFormat;

    /**
     * 返回值
     */
    private String returns;
}
