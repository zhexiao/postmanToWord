package com.zhexiao.convert.entity.postman;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@ApiModel(description = "postman外层数据")
@Data
@Accessors(chain = true)
public class Info {

    @ApiModelProperty(notes = "接口名")
    private String name;

    @ApiModelProperty(notes = "接口规范")
    private String schema;

    @ApiModelProperty(notes = "接口ID")
    private String postmanId;
}
