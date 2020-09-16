package com.zhexiao.convert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.ParaStyle;
import com.zhexiao.convert.entity.TableApiVal;
import com.zhexiao.convert.entity.postman.Item;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.service.ConvertService;
import com.zhexiao.convert.utils.json.JsonUtils;
import com.zhexiao.convert.utils.word.ApiWord;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Service
public class ConvertServiceImpl implements ConvertService {

    private static final Logger logger = LoggerFactory.getLogger(ConvertServiceImpl.class);

    @Autowired
    JsonUtils jsonUtils;

    /**
     * 解析上传的文件数据
     * @param file
     * @return
     */
    @Override
    public Result<Postman> upload(MultipartFile file) {
        Postman postman = getData(file);
        return Result.success(postman);
    }

    @Override
    public Result<String> writeWord(MultipartFile file) throws Exception {
        Postman postman = getData(file);
        logger.info(postman.toString());

        //生成word
        ApiWord apiWord = new ApiWord();
        ParaStyle titleParaStyle = new ParaStyle().setAlign(ParagraphAlignment.CENTER).setBold(true).setFontSize(18);

        //标题
        apiWord.createParagraph("API接口文档 - "+postman.getInfo().getName(), titleParaStyle);
        apiWord.createParagraph("");

        //表格
        List<Item> items = postman.getItem();
        for (Item item : items) {
            logger.info(item.toString());
            TableApiVal tableApiVal = new TableApiVal()
                    .setInterfaceName(item.getName())
                    .setInterfaceUrl(item.getRequest().getUrl().getRaw())
                    .setInterfaceAction(item.getName())
                    .setMethod(item.getRequest().getMethod())
                    .setCallSystem("")
                    .setDataFormat("")
                    .setParameters(item.getRequest().getUrl().getQuery())
                    .setReturns("JSON")
                    .setReturnSample("")
                    .setCallSample(item.getRequest().getUrl().getRaw())
                    .setExceptionScene("");

            logger.info(item.getName());
            //根据值创建
            apiWord.createApiTable(tableApiVal);
            apiWord.createParagraph("");
            apiWord.createParagraph("");
        }
        logger.info("---------------word表格创建完毕--------------");

        apiWord.export("./words/" + System.currentTimeMillis() + ".docx");

        return Result.success("ok");
    }

    /**
     * 通过文件读取数据
     * @param file
     * @return
     */
    private Postman getData(MultipartFile file){
        String res = jsonUtils.read(file);

        JSONObject jsonObject = JSON.parseObject(res);
        Postman postman = JSON.toJavaObject(jsonObject, Postman.class);
        return postman;
    }
}
