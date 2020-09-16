package com.zhexiao.convert.entity;

import com.zhexiao.convert.entity.postman.Parameter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表数据
 *
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Data
@Accessors(chain = true)
public class TableApiVal implements TableVal {
    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口url
     */
    private String interfaceUrl;

    /**
     * 接口功能
     */
    private String interfaceAction;

    /**
     * 接口方法
     */
    private String method;

    /**
     * 调用系统
     */
    private String callSystem;

    /**
     * 数据格式
     */
    private String dataFormat;

    /**
     * 参数
     */
    private List<Parameter> parameters;

    /**
     * 返回值
     */
    private String returns;

    /**
     * 返回示例
     */
    private String returnSample;

    /**
     * 调用举例
     */
    private String callSample;

    /**
     * 异常场景
     */
    private String exceptionScene;
}
