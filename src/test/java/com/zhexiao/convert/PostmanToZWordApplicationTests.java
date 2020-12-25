package com.zhexiao.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.entity.PStyle;
import com.zhexiao.convert.entity.TableContext;
import com.zhexiao.convert.entity.postman.Info;
import com.zhexiao.convert.entity.postman.Parameter;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.component.ReadFile;
import com.zhexiao.convert.utils.ZWord;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class PostmanToZWordApplicationTests {

    @Autowired
    ReadFile readFile;

    @Test
    void t1() {
        String str = readFile.read("./guide/postman/demo.json");
        JSONObject jsonObject = JSON.parseObject(str);
        Postman postman = JSON.toJavaObject(jsonObject, Postman.class);

        System.out.println(postman.toString());

        JSONObject info = jsonObject.getJSONObject("info");
        Info infoObj = JSON.toJavaObject(info, Info.class);
        System.out.println(infoObj.toString());
    }

    @Test
    void t2() {
        ZWord zWord = new ZWord();

        //创建段落
        PStyle pStyle = new PStyle().setAlign(ParagraphAlignment.CENTER).setBold(true);
        zWord.createParagraph("接口文档", pStyle);

        //创建表格
        zWord.createTable(11, 2, getTitle(), getData());

        //导出
        zWord.export("./abc.docs");
    }

    @Test
    void t3() {
        String resap = "{\n    \"code\": 400,\n    \"message\": null,\n    \"data\": null\n}";
        List<String> strings = Arrays.asList(resap.split("\n"));
        for (String string : strings) {
            System.out.println(string);
        }
    }

    private TableContext getData() {
        //参数
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter().setKey("identity").setValue("18171202512"));
        parameters.add(new Parameter().setKey("userName").setValue("xxxxx"));
        parameters.add(new Parameter().setKey("snNumber").setValue("200915-ST51010"));

        TableContext tableContext = new TableContext()
                .setInterfaceName("用户登录")
                .setInterfaceUrl("/v2/auth/login")
                .setInterfaceAction("用户登录并返回对应的角色")
                .setMethod("POST")
                .setCallSystem("")
                .setDataFormat("")
                .setParameters(parameters)
                .setReturns("JSON")
                .setReturnSample(null)
                .setCallSample("")
                .setExceptionScene("");

        return tableContext;
    }

    public ArrayList<String> getTitle() {
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
}
