package com.zhexiao.convert.utils;

import com.zhexiao.convert.entity.TStyle;
import com.zhexiao.convert.entity.TableContext;
import com.zhexiao.convert.entity.postman.Parameter;
import com.zhexiao.convert.entity.postman.Response;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhe.xiao
 * @date 2020-12-25
 * @description
 */
public class ZTable extends ZBase {
    private static final Logger log = LoggerFactory.getLogger(ZTable.class);

    //所属的word
    private XWPFTable table = null;

    //表格的宽度
    private Integer tableWidth = 9072;

    //cell宽度，多个cell用逗号分隔
    private int[] cellWidth = new int[]{800, 5000};

    //表格行
    private Integer rowNum;

    //表格列
    private Integer colNum;

    //设置行的 标题内容 样式
    private TStyle rowTitleStyle;

    //设置行的 数据内容 样式
    private TStyle rowContentStyle;

    public ZTable(ZWord zWord) {
        this.rowNum = 11;
        this.colNum = 2;

        //获得表格
        this.table = zWord.getDocument().createTable(this.rowNum, this.colNum);
    }

    public ZTable(ZWord zWord, Integer rowNum, Integer colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;

        //获得表格
        this.table = zWord.getDocument().createTable(this.rowNum, this.colNum);
    }

    /**
     * 设置整个table的宽度
     */
    private void setTableWidth() {
        CTTbl ctTbl = this.table.getCTTbl();

        //table属性
        CTTblPr tblPr = ctTbl.getTblPr();

        //设置表格中cell的长度不随着文字变化而变化
        CTTblLayoutType layoutType = tblPr.addNewTblLayout();
        layoutType.setType(STTblLayoutType.FIXED);

        //设置宽度
        CTTblWidth tblW = tblPr.getTblW();
        tblW.setW(new BigInteger("" + this.tableWidth));
        tblW.setType(STTblWidth.DXA);
    }

    /**
     * 设置 table 里面每个cell的宽度
     */
    private void setCellWidth() {
        List<XWPFTableRow> rows = this.table.getRows();

        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();

            //验证cell数与cell宽度值数量是否一致
            if (this.cellWidth.length != cells.size()) {
                log.info("cell的宽度数组与cell数量不匹配:{}, {}", this.cellWidth.length, cells.size());
                return;
            }

            //设置cell宽度值
            for (int i = 0; i < cells.size(); i++) {
                XWPFTableCell cell = cells.get(i);
                cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("" + this.cellWidth[i]));
            }
        }
    }

    /**
     * API文档table的创建
     *
     * @param tableContext
     */
    public void createTable(List<String> rowHeaders, TableContext tableContext) {
        //设置整个表格的宽度
        this.setTableWidth();

        //设置每个表格里面cell的宽度
        this.setCellWidth();

        //设置标题和内容
        this.createTableTitle(0, rowHeaders);
        this.createTableContext(tableContext);
    }

    /**
     * 文档标题
     */
    private void createTableTitle(Integer colNum, List<String> rowHeaders) {
        if (rowHeaders.isEmpty()) {
            log.info("行头数为空");
            return;
        }

        if (rowHeaders.size() != this.rowNum) {
            log.info("行头数与行数不匹配");
            return;
        }

        //样式
        if (null == this.rowTitleStyle) {
            this.rowTitleStyle = new TStyle()
                    .setAlign(ParagraphAlignment.CENTER)
                    .setFontSize(9)
                    .setFontFamily("Microsoft YaHei");
        }

        //设置某列的行值
        this.setColText(colNum, rowHeaders);
    }

    /**
     * 设置api文档的值
     *
     * @param tableContext
     */
    private void createTableContext(TableContext tableContext) {
        if (null == this.rowContentStyle) {
            this.rowContentStyle = new TStyle()
                    .setAlign(ParagraphAlignment.LEFT)
                    .setIndentLeft(100)
                    .setFontSize(9)
                    .setFontColor("555555")
                    .setFontFamily("Microsoft YaHei");
        }

        List<XWPFTableRow> rows = this.table.getRows();

        for (int i = 0; i < rows.size(); i++) {
            XWPFTableCell cell = rows.get(i).getCell(1);

            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();

            //配置样式
            this.configureTextStyle(paragraph, run, this.rowContentStyle);

            switch (i) {
                case 0:
                    run.setText(tableContext.getInterfaceName());
                    break;
                case 1:
                    run.setText(tableContext.getInterfaceUrl());
                    break;
                case 2:
                    run.setText(tableContext.getInterfaceAction());
                    break;
                case 3:
                    run.setText(tableContext.getMethod());
                    break;
                case 4:
                    run.setText(tableContext.getCallSystem());
                    break;
                case 5:
                    run.setText(tableContext.getDataFormat());
                    break;
                case 6:
                    List<Parameter> parameters = tableContext.getParameters();
                    if (parameters != null && !parameters.isEmpty()) {
                        for (int i1 = 0; i1 < parameters.size(); i1++) {
                            Parameter parameter = parameters.get(i1);
                            run.setText(parameter.getKey() + "  [" + parameter.getValue() + "]");

                            if (i1 < parameters.size() - 1) {
                                run.addBreak(BreakType.TEXT_WRAPPING);
                            }
                        }
                    } else {
                        run.setText("");
                    }

                    break;
                case 7:
                    run.setText(tableContext.getReturns());
                    break;
                case 8:
                    if (tableContext.getReturnSample() != null && (!tableContext.getReturnSample().isEmpty())) {
                        List<Response> returnSample = tableContext.getReturnSample();
                        for (Response response : returnSample) {
                            run.setText(response.getName());
                            run.addBreak();

                            String responseBody = response.getBody();
                            List<String> respStrList = Arrays.asList(responseBody.split("\n"));
                            for (String s : respStrList) {
                                run.setText(s);
                                run.addBreak();
                            }

                            run.addBreak();
                        }
                    }
                    break;
                case 9:
                    run.setText(tableContext.getCallSample());
                    break;
                case 10:
                    run.setText(tableContext.getExceptionScene());
                    break;
                default:
                    continue;
            }
        }
    }

    /**
     * 设置第 col 列的值
     *
     * @param col
     * @param rowHeader
     */
    private void setColText(int col, List<String> rowHeader) {
        List<XWPFTableRow> rows = table.getRows();

        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            XWPFTableCell cell = row.getCell(col);

            //设置值和属性
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();
            run.setText(rowHeader.get(i));

            //配置样式
            this.configureTextStyle(paragraph, run, this.rowTitleStyle);
        }
    }

    public TStyle getRowTitleStyle() {
        return rowTitleStyle;
    }

    public void setRowTitleStyle(TStyle rowTitleStyle) {
        this.rowTitleStyle = rowTitleStyle;
    }

    public TStyle getRowContentStyle() {
        return rowContentStyle;
    }

    public void setRowContentStyle(TStyle rowContentStyle) {
        this.rowContentStyle = rowContentStyle;
    }
}
