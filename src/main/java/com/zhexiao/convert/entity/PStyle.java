package com.zhexiao.convert.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 *
 * 段落的样式
 *
 * @Auther: zhe.xiao
 * @Date: 2020/09/09
 * @Description
 */
@Data
@Accessors(chain = true)
public class PStyle {
    /**
     * 字体大小
     */
    private Integer fontSize;

    /**
     * 加粗
     */
    private Boolean bold;

    /**
     * 偏移
     */
    private ParagraphAlignment align;
}