package com.zhexiao.convert;

import com.zhexiao.convert.entity.TableApiVal;
import com.zhexiao.convert.utils.word.ApiWord;
import com.zhexiao.convert.entity.ParaStyle;
import com.zhexiao.convert.entity.Parameter;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

// @SpringBootTest
class PostmanToWordApplicationTests {

    @Test
    void t1() {

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
                    .setReturnSample("成功\n" +
                            "{\n" +
                            "    \"code\": \"U000000\",\n" +
                            "    \"msgCode\": \"success.id\",\n" +
                            "    \"data\": {\n" +
                            "        \"accountId\": \"210921381917888513\",\n}")
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
