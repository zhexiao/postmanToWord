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
public class TextStyle {
    /**
     * 超链接
     */
    private String url;

    /**
     * 文本内容
     */
    private String text;

    /**
     * 字体
     */
    private String fontFamily;

    /**
     * 字体大小
     */
    private String fontSize;

    /**
     * 字体颜色
     */
    private String colorVal;

    /**
     * 底纹颜色
     */
    private String shdColor;

    /**
     * 文本位置
     */
    private int position;

    /**
     * 间距
     */
    private int spacingValue;

    /**
     * 缩进
     */
    private int indent;

    /**
     * 加粗
     */
    private boolean isBlod;

    /**
     * 下划线
     */
    private boolean isUnderLine;

    /**
     * 下划线颜色
     */
    private boolean underLineColor;

    /**
     * 斜体
     */
    private boolean isItalic;

    /**
     * 删除线
     */
    private boolean isStrike;

    /**
     * 双删除线
     */
    private boolean isDStrike;

    /**
     * 阴影
     */
    private boolean isShadow;

    /**
     * 隐藏
     */
    private boolean isVanish;

    /**
     * 阳文
     */
    private boolean isEmboss;

    /**
     * 阴文
     */
    private boolean isImprint;

    /**
     * 空心
     */
    private boolean isOutline;

    /**
     * 突出显示文本
     */
    private boolean isHightLight;

    /**
     * 底纹
     */
    private boolean isShd;
}