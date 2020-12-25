package com.zhexiao.convert.utils;

import com.zhexiao.convert.entity.PStyle;
import com.zhexiao.convert.entity.TStyle;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author zhe.xiao
 * @date 2020-12-25
 * @description
 */
public class ZBase {

    /**
     * 配置文本样式
     *
     * @param paragraph
     * @param run
     * @param tStyle
     */
    protected void configureTextStyle(XWPFParagraph paragraph, XWPFRun run, TStyle tStyle) {
        if (tStyle.getAlign() != null) {
            paragraph.setAlignment(tStyle.getAlign());
        }

        if (tStyle.getIndentLeft() != null) {
            paragraph.setIndentationLeft(tStyle.getIndentLeft());
        }

        if (tStyle.getFontSize() != null) {
            run.setFontSize(tStyle.getFontSize());
        }

        if (!"".equals(tStyle.getFontFamily()) && tStyle.getFontFamily() != null) {
            run.setFontFamily(tStyle.getFontFamily());
        }

        if (!"".equals(tStyle.getFontColor()) && tStyle.getFontColor() != null) {
            run.setColor(tStyle.getFontColor());
        }
    }

    /**
     * 配置段落类型里面的文本样式
     * @param paragraphRun
     * @param pStyle
     */
    protected void configureParagraphRunStyle(XWPFRun paragraphRun, PStyle pStyle) {
        //加粗
        if (pStyle.getBold() != null) {
            paragraphRun.setBold(true);
        }

        //字体大小
        if (pStyle.getFontSize() != null && pStyle.getFontSize() > 0) {
            paragraphRun.setFontSize(pStyle.getFontSize());
        }
    }
}
