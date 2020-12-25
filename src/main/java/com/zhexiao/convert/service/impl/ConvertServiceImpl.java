package com.zhexiao.convert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.component.UploadFile;
import com.zhexiao.convert.config.AppConfig;
import com.zhexiao.convert.entity.PStyle;
import com.zhexiao.convert.entity.TableContext;
import com.zhexiao.convert.entity.dto.ConvertDTO;
import com.zhexiao.convert.entity.postman.Item;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.service.ConvertService;
import com.zhexiao.convert.component.ReadFile;
import com.zhexiao.convert.utils.ZWord;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhexiao
 * @date 2020-09-15 22:17
 * @description
 */
@Service
public class ConvertServiceImpl implements ConvertService {

    private static final Logger logger = LoggerFactory.getLogger(ConvertServiceImpl.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    ReadFile readFile;

    @Autowired
    UploadFile uploadFile;


    /**
     * 解析上传的文件数据
     *
     * @param file
     * @return
     */
    @Override
    public Result<Postman> upload(MultipartFile file) {
        Postman postman = this.parsePostmanJson(file);
        return Result.success(postman);
    }

    /**
     * 生成word数据
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Result<String> writeWord(MultipartFile file, ConvertDTO convertDTO) {
        Postman postman = this.parsePostmanJson(file);

        ZWord zWord = new ZWord();

        //创建段落
        PStyle pStyle = new PStyle().setAlign(ParagraphAlignment.CENTER).setBold(true).setFontSize(18);
        zWord.createParagraph("API接口文档 - " + postman.getInfo().getName(), pStyle);
        zWord.createParagraph("", null);

        //表格
        List<Item> items = postman.getItem();
        for (Item item : items) {
            //根据值创建
            zWord.createTable(11, 2, this.getTableTitle(), this.getTableContext(item, convertDTO));
            zWord.createParagraph("", null);
            zWord.createParagraph("", null);
        }
        logger.info("---------------word表格创建完毕--------------");

        //保存文件
        String filename = postman.getInfo().getName() + "_" + System.currentTimeMillis() + ".docx";
        String dest = appConfig.getFileDest() + File.separator + filename;
        zWord.export(dest);
        logger.info("---------------word导出完毕--------------");

        return Result.success(dest);
    }

    /**
     * 读取接口内容
     *
     * @param item
     * @param convertDTO
     * @return
     */
    private TableContext getTableContext(Item item, ConvertDTO convertDTO) {
        //得到接口URL
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getRequest().getUrl().getHost().get(0));
        List<String> path = item.getRequest().getUrl().getPath();
        if (!path.isEmpty()) {
            path.stream().forEach(e -> {
                stringBuilder.append("/");
                stringBuilder.append(e);
            });
        }

        //接口内容

        return new TableContext()
                .setInterfaceName(item.getName())
                .setInterfaceUrl(stringBuilder.toString())
                .setInterfaceAction(item.getName())
                .setMethod(item.getRequest().getMethod())
                .setCallSystem(convertDTO.getCallSystem())
                .setDataFormat(convertDTO.getDataFormat())
                .setParameters(item.getRequest().getUrl().getQuery())
                .setReturns(convertDTO.getReturns())
                .setReturnSample(item.getResponse())
                .setCallSample(item.getRequest().getUrl().getRaw())
                .setExceptionScene("");
    }

    /**
     * 读取接口表格标题
     *
     * @return
     */
    public List<String> getTableTitle() {
        ArrayList<String> rowHeaders = new ArrayList<>();
        rowHeaders.add("接口名称");
        rowHeaders.add("接口URL");
        rowHeaders.add("接口功能");
        rowHeaders.add("请求方式");
        rowHeaders.add("调用系统");
        rowHeaders.add("数据格式");
        rowHeaders.add("参数");
        rowHeaders.add("返回值");
        rowHeaders.add("返回示例");
        rowHeaders.add("调用举例");
        rowHeaders.add("异常场景");
        return rowHeaders;
    }

    /**
     * 通过文件读取数据
     *
     * @param file
     * @return
     */
    private Postman parsePostmanJson(MultipartFile file) {
        String res = readFile.read(file);

        JSONObject jsonObject = JSON.parseObject(res);
        return JSON.toJavaObject(jsonObject, Postman.class);
    }
}
