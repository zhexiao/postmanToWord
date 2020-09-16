package com.zhexiao.convert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.ParaStyle;
import com.zhexiao.convert.entity.TableApiVal;
import com.zhexiao.convert.entity.postman.Item;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import com.zhexiao.convert.service.ConvertService;
import com.zhexiao.convert.utils.json.JsonUtils;
import com.zhexiao.convert.utils.word.ApiWord;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Service
public class ConvertServiceImpl implements ConvertService {

    private static final Logger logger = LoggerFactory.getLogger(ConvertServiceImpl.class);

    @Value("${app.fileVisitHost}")
    private String fileVisitHost;

    @Value("${app.fileDest}")
    private String fileDest;

    @Value("${app.filePathRoute}")
    private String filePathRoute;

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

    /**
     * 生成word数据
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Result<UploaderFileVO> writeWord(MultipartFile file) throws Exception {
        Postman postman = getData(file);

        //生成word
        ApiWord apiWord = new ApiWord();
        ParaStyle titleParaStyle = new ParaStyle().setAlign(ParagraphAlignment.CENTER).setBold(true).setFontSize(18);

        //标题
        apiWord.createParagraph("API接口文档 - "+postman.getInfo().getName(), titleParaStyle);
        apiWord.createParagraph("");

        //表格
        List<Item> items = postman.getItem();
        for (Item item : items) {
            logger.info(item.getName());

            //得到接口URL
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getRequest().getUrl().getHost().get(0));
            List<String> path = item.getRequest().getUrl().getPath();
            if(!path.isEmpty()){

                path.stream().forEach(e -> {
                    stringBuilder.append("/");
                    stringBuilder.append(e);
                });
            }

            TableApiVal tableApiVal = new TableApiVal()
                    .setInterfaceName(item.getName())
                    .setInterfaceUrl(stringBuilder.toString())
                    .setInterfaceAction(item.getName())
                    .setMethod(item.getRequest().getMethod())
                    .setCallSystem("Android, iOS")
                    .setDataFormat("JSON")
                    .setParameters(item.getRequest().getUrl().getQuery())
                    .setReturns("JSON")
                    .setReturnSample("")
                    .setCallSample(item.getRequest().getUrl().getRaw())
                    .setExceptionScene("");

            logger.info(item.toString());
            //根据值创建
            apiWord.createApiTable(tableApiVal);
            apiWord.createParagraph("");
            apiWord.createParagraph("");
        }
        logger.info("---------------word表格创建完毕--------------");

        //保存文件
        String filename = postman.getInfo().getName() + "_" + System.currentTimeMillis() + ".docx";
        String visitPath = fileDest + "/" + filename;
        apiWord.export(visitPath);
        logger.info("---------------word导出完毕--------------");

        //返回数据
        UploaderFileVO fileVO = new UploaderFileVO()
                .setFullpath(visitPath)
                .setFilename(filename)
                .setDest(fileDest)
                .setOriginFilename(file.getOriginalFilename())
                .setCreateDatetime(LocalDateTime.now())
                .setVisitPath(fileVisitHost + "" + filePathRoute + "" +filename);
        return Result.success(fileVO);
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
