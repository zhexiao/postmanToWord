package com.zhexiao.convert.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/09
 * @Description
 */
@Data
@Accessors(chain = true)
public class ParagraphStyle {
    /**
     * 是否缩进
     */
    private boolean isSpace;

    /**
     * 段前磅数
     */
    private String before;

    /**
     * 段后磅数
     */
    private String after;

    /**
     * 段前行数
     */
    private String beforeLines;

    /**
     * 段后行数
     */
    private String afterLines;

    /**
     * 是否间距
     */
    private boolean isLine;

    /**
     * 间距距离
     */
    private String line;

    //段落缩进信息
    private String firstLine;
    private String firstLineChar;
    private String hanging;
    private String hangingChar;
    private String right;
    private String rightChar;
    private String left;
    private String leftChar;

}