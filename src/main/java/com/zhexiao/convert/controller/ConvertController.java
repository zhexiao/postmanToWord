package com.zhexiao.convert.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import com.zhexiao.convert.service.impl.ConvertServiceImpl;
import com.zhexiao.convert.utils.file.FileUploaderUtil;
import com.zhexiao.convert.utils.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/15
 * @Description
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConvertController {

    @Autowired
    ConvertServiceImpl convertService;

    /**
     * 读取json字符串
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<Postman> upload(MultipartFile file){
        return convertService.upload(file);
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Result<String> writeWord(MultipartFile file){
        try {
            return convertService.writeWord(file);
        } catch (Exception e) {
            return Result.failed(e.getMessage());
        }
    }
}
