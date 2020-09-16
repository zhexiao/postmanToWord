package com.zhexiao.convert.entity;

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
@Data
@Accessors(chain = true)
public class TextStyle {
    /**
     * 字体大小
     */
    private Integer fontSize;

    /**
     * 字体
     */
    private String fontFamily;

    /**
     * 加粗
     */
    private Boolean bold;

    /**
     * 偏移
     */
    private ParagraphAlignment align;

    /**
     * 字体的缩进
     */
    private Integer indentLeft;
}