package com.zhexiao.convert.utils.word;

import com.zhexiao.convert.entity.TableApiVal;
import com.zhexiao.convert.entity.ParaStyle;
import com.zhexiao.convert.entity.postman.Parameter;
import com.zhexiao.convert.entity.TextStyle;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhexiao
 * @date 2020-09-15 22:17
 **/
public class ApiWord {

    private static final Logger logger = LoggerFactory.getLogger(ApiWord.class);


    private XWPFDocument document = null;

    public ApiWord() {
        document = new XWPFDocument();
    }

    /**
     * 创建一个段落
     *
     * @param text
     * @throws Exception
     */
    public void createParagraph(String text) throws Exception {
        ParaStyle paraStyle = new ParaStyle();
        createParagraph(text, paraStyle);
    }

    /**
     * 创建一个段落
     *
     * @param text
     * @param paraStyle
     * @throws Exception
     */
    public void createParagraph(String text, ParaStyle paraStyle) throws Exception {
        //添加段落
        XWPFParagraph paragraph = document.createParagraph();

        //段落属性
        if (paraStyle.getAlign() != null) {
            paragraph.setAlignment(paraStyle.getAlign());
        }

        //设置段落文本内容
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText(text);

        //设置段落文本属性
        if (paraStyle.getBold() != null) {
            paragraphRun.setBold(true);
        }

        if (paraStyle.getFontSize() != null && paraStyle.getFontSize() > 0) {
            paragraphRun.setFontSize(paraStyle.getFontSize());
        }
    }

    /**
     * 创建一个API文档的table
     *
     * @param tableVal
     * @throws Exception
     */
    public void createApiTable(TableApiVal tableVal) throws Exception {
        createApiTable(11, 2, tableVal);
    }

    /**
     * API文档table的创建
     *
     * @param rowNum
     * @param colNum
     * @param tableVal
     * @throws Exception
     */
    private void createApiTable(Integer rowNum, int colNum, TableApiVal tableVal) throws Exception {
        //获得表格
        XWPFTable table = document.createTable(rowNum, colNum);

        //设置整个表格的宽度
        setTableWidth(table, 9072);

        //设置每个表格里面cell的宽度
        setCellWidth(table, new int[]{800, 5000});

        //设置列的值
        setApiTableTitle(table);
        setApiTableValue(table, tableVal);
    }

    /**
     * 设置api文档第二列的值
     * @param table
     * @param tableVal
     */
    private void setApiTableValue(XWPFTable table, TableApiVal tableVal) {
        TextStyle textStyle = new TextStyle()
                .setAlign(ParagraphAlignment.LEFT)
                .setIndentLeft(100)
                .setFontSize(9)
                .setFontFamily("Microsoft YaHei");

        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableCell cell = rows.get(i).getCell(1);

            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();

            //配置样式
            configureTextStyle(paragraph, run, textStyle);

            switch (i){
                case 0:
                    run.setText(tableVal.getInterfaceName());
                    break;
                case 1:
                    run.setText(tableVal.getInterfaceUrl());
                    break;
                case 2:
                    run.setText(tableVal.getInterfaceAction());
                    break;
                case 3:
                    run.setText(tableVal.getMethod());
                    break;
                case 4:
                    run.setText(tableVal.getCallSystem());
                    break;
                case 5:
                    run.setText(tableVal.getDataFormat());
                    break;
                case 6:
                    List<Parameter> parameters = tableVal.getParameters();
                    if(parameters!=null && parameters.size() > 0){
                        for (int i1 = 0; i1 < parameters.size(); i1++) {
                            Parameter parameter = parameters.get(i1);
                            run.setText(parameter.getKey() +"      " + parameter.getValue());

                            if(i1 < parameters.size()-1){
                                run.addBreak(BreakType.TEXT_WRAPPING);
                            }
                        }
                    }else{
                        run.setText("");
                    }

                    break;
                case 7:
                    run.setText(tableVal.getReturns());
                    break;
                case 8:
                    run.setText(tableVal.getReturnSample());
                    break;
                case 9:
                    run.setText(tableVal.getCallSample());
                    break;
                case 10:
                    run.setText(tableVal.getExceptionScene());
                    break;
            }
        }
    }

    /**
     * 设置api文档的第一列命名
     *
     * @param table
     */
    private void setApiTableTitle(XWPFTable table) {
        //设置第一列的名称
        ArrayList<String> strings = new ArrayList<>();
        strings.add("接口名称");
        strings.add("接口URL");
        strings.add("接口功能");
        strings.add("请求方式");
        strings.add("调用系统");
        strings.add("数据格式");
        strings.add("参数");
        strings.add("返回值");
        strings.add("返回示例");
        strings.add("调用举例");
        strings.add("异常场景");

        //样式
        TextStyle ts1 = new TextStyle()
                .setAlign(ParagraphAlignment.CENTER)
                .setFontSize(9)
                .setFontFamily("Microsoft YaHei");

        //设置
        setColText(table, 0, strings, ts1);
    }

    /**
     * 设置第 col 列的值
     *
     * @param table
     * @param col
     * @param data
     */
    private void setColText(XWPFTable table, int col, ArrayList<String> data) {
        setColText(table, col, data, new TextStyle());
    }

    /**
     * 设置第 col 列的值
     *
     * @param table
     * @param col
     * @param data
     */
    private void setColText(XWPFTable table, int col, ArrayList<String> data, TextStyle textStyle) {
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            XWPFTableCell cell = row.getCell(col);

            //设置值和属性
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();
            run.setText(data.get(i));

            //配置样式
            configureTextStyle(paragraph, run, textStyle);
        }
    }

    /**
     * 配置文本样式
     * @param paragraph
     * @param run
     * @param textStyle
     */
    private void configureTextStyle(XWPFParagraph paragraph, XWPFRun run, TextStyle textStyle) {
        if (textStyle.getAlign() != null) {
            paragraph.setAlignment(textStyle.getAlign());
        }

        if (textStyle.getIndentLeft() != null) {
            paragraph.setIndentationLeft(textStyle.getIndentLeft());
        }

        if (textStyle.getFontSize() != null) {
            run.setFontSize(textStyle.getFontSize());
        }

        if (!"".equals(textStyle.getFontFamily()) && textStyle.getFontFamily() != null) {
            run.setFontFamily(textStyle.getFontFamily());
        }
    }

    /**
     * 设置 table 里面每个cell的宽度
     *
     * @param table
     * @param cellWidths
     * @throws Exception
     */
    private void setCellWidth(XWPFTable table, int[] cellWidths) throws Exception {
        List<XWPFTableRow> rows = table.getRows();
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            if (cellWidths.length != cells.size()) {
                throw new Exception("cell的宽度数组与cell数量不匹配");
            }

            for (int i = 0; i < cells.size(); i++) {
                XWPFTableCell cell = cells.get(i);
                cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("" + cellWidths[i]));
            }
        }
    }

    /**
     * 设置整个table的宽度
     *
     * @param table
     * @param width
     */
    private void setTableWidth(XWPFTable table, Integer width) {
        CTTbl ctTbl = table.getCTTbl();

        //table属性
        CTTblPr tblPr = ctTbl.getTblPr();

        //设置表格中cell的长度不随着文字变化而变化
        CTTblLayoutType layoutType = tblPr.addNewTblLayout();
        layoutType.setType(STTblLayoutType.FIXED);

        //设置宽度
        CTTblWidth tblW = tblPr.getTblW();
        tblW.setW(new BigInteger("" + width));
        tblW.setType(STTblWidth.DXA);
    }

    /**
     * 导出
     *
     * @throws Exception
     */
    public void export(String path) throws Exception {
        saveDocument(document, path);
    }

    /**
     * 保存文档
     *
     * @param document
     * @param savePath
     * @throws IOException
     */
    public void saveDocument(XWPFDocument document, String savePath) throws IOException {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(savePath);
            document.write(fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}