package com.zhexiao.convert.utils;

import com.zhexiao.convert.entity.PStyle;
import com.zhexiao.convert.entity.TableContext;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 生成WORD文档
 *
 * @author zhexiao
 * @date 2020-09-15 22:17
 * @description
 */
public class ZWord {

    private static final Logger log = LoggerFactory.getLogger(ZWord.class);

    private XWPFDocument document;

    private ZParagraph zParagraph = null;

    private ZTable zTable = null;

    public ZWord() {
        document = new XWPFDocument();
    }

    /**
     * 创建段落
     *
     * @param text
     */
    public void createParagraph(String text, PStyle pStyle) {
        this.zParagraph = new ZParagraph(this);

        if (null == pStyle) {
            pStyle = new PStyle();
        }

        this.zParagraph.createParagraph(text, pStyle);
    }

    /**
     * 创建表格
     *
     * @param rowNum
     * @param colNum
     * @param rowHeaders
     * @param tableContext
     */
    public void createTable(Integer rowNum, Integer colNum, List<String> rowHeaders, TableContext tableContext) {
        this.zTable = new ZTable(this, rowNum, colNum);

        //样式使用默认样式
        this.zTable.setRowTitleStyle(null);
        this.zTable.setRowContentStyle(null);

        this.zTable.createTable(rowHeaders, tableContext);
    }

    /**
     * 导出
     */
    public void export(String path) {
        try (OutputStream fos = new FileOutputStream(path)) {
            document.write(fos);
        } catch (Exception e) {
            log.info("保存文件失败,{}", e.getMessage());
        }
    }


    public XWPFDocument getDocument() {
        return document;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public ZParagraph getzParagraph() {
        return zParagraph;
    }

    public void setzParagraph(ZParagraph zParagraph) {
        this.zParagraph = zParagraph;
    }

    public ZTable getzTable() {
        return zTable;
    }

    public void setzTable(ZTable zTable) {
        this.zTable = zTable;
    }
}