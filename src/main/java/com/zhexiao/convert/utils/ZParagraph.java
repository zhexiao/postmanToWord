package com.zhexiao.convert.utils;

import com.zhexiao.convert.entity.PStyle;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author zhe.xiao
 * @date 2020-12-25
 * @description
 */
public class ZParagraph extends ZBase {

    private ZWord zWord = null;

    public ZParagraph(ZWord zWord) {
        this.zWord = zWord;
    }

    /**
     * 创建一个段落，定义样式
     *
     * @param text
     */
    public void createParagraph(String text, PStyle pStyle) {
        //添加段落
        XWPFParagraph paragraph = this.zWord.getDocument().createParagraph();

        //段落属性
        if (pStyle.getAlign() != null) {
            paragraph.setAlignment(pStyle.getAlign());
        }

        //设置段落文本内容
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText(text);

        this.configureParagraphRunStyle(paragraphRun, pStyle);
    }
}
