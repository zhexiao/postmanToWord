package com.zhexiao.convert.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 *
 * 文本的样式
 *
 * @Auther: zhe.xiao
 * @Date: 2020/09/09
 * @Description
 */
@ApiModel(description = "文本样式")
@Data
@Accessors(chain = true)
public class TextStyle {

    @ApiModelProperty(notes = "字体大小", example = "12")
    private Integer fontSize;

    @ApiModelProperty(notes = "字体")
    private String fontFamily;

    @ApiModelProperty(notes = "字体颜色", example = "999999")
    private String fontColor;

    @ApiModelProperty(notes = "加粗", example = "true")
    private Boolean bold;

    @ApiModelProperty(notes = "偏移")
    private ParagraphAlignment align;

    @ApiModelProperty(notes = "缩进")
    private Integer indentLeft;
}