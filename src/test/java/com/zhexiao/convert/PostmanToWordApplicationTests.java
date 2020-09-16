package com.zhexiao.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.entity.TableApiVal;
import com.zhexiao.convert.entity.postman.Info;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.utils.json.JsonUtils;
import com.zhexiao.convert.utils.word.ApiWord;
import com.zhexiao.convert.entity.ParaStyle;
import com.zhexiao.convert.entity.postman.Parameter;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

// @SpringBootTest
class PostmanToWordApplicationTests {

    @Test
    void t1() {
        JsonUtils jsonUtil = new JsonUtils();
        try {
            String str = jsonUtil.read("3DP.postman_collection.json");
            JSONObject jsonObject = JSON.parseObject(str);
            Postman postman = JSON.toJavaObject(jsonObject, Postman.class);
            System.out.println(postman.toString());

            JSONObject info = jsonObject.getJSONObject("info");

            Info infoObj = JSON.toJavaObject(info, Info.class);
            System.out.println(infoObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void t2(){
        ApiWord apiWord = new ApiWord();

        try {
            ParaStyle titleParaStyle = new ParaStyle().setAlign(ParagraphAlignment.CENTER).setBold(true);
            apiWord.createParagraph("API接口文档 - 测试版", titleParaStyle);

            //参数
            ArrayList<Parameter> parameters = new ArrayList<>();
            parameters.add(new Parameter().setKey("identity").setValue("18171202512"));
            parameters.add(new Parameter().setKey("userName").setValue("xxxxx"));
            parameters.add(new Parameter().setKey("snNumber").setValue("200915-ST51010"));

            TableApiVal tableApiVal = new TableApiVal()
                    .setInterfaceName("用户登录")
                    .setInterfaceUrl("/v2/auth/login")
                    .setInterfaceAction("用户登录并返回对应的角色")
                    .setMethod("POST")
                    .setCallSystem("")
                    .setDataFormat("")
                    .setParameters(parameters)
                    .setReturns("JSON")
                    .setReturnSample("")
                    .setCallSample("")
                    .setExceptionScene("");
            apiWord.createApiTable(tableApiVal);

            apiWord.createParagraph("");
            apiWord.createApiTable(tableApiVal);

            apiWord.createParagraph("");
            apiWord.createApiTable(tableApiVal);

            apiWord.export("./words/" + System.currentTimeMillis() + ".docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
