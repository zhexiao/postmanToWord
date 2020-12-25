package com.zhexiao.convert.entity;

import com.zhexiao.convert.entity.postman.Parameter;
import com.zhexiao.convert.entity.postman.Response;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表数据
 *
 * @author zhexiao
 * @date 2020-09-15 22:17
 * @description
 */
@Data
@Accessors(chain = true)
public class TableContext {
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
    private List<Response> returnSample;

    /**
     * 调用举例
     */
    private String callSample;

    /**
     * 异常场景
     */
    private String exceptionScene;
}
