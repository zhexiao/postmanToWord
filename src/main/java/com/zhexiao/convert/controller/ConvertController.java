package com.zhexiao.convert.controller;

import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.dto.TableApiValDTO;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import com.zhexiao.convert.exceptions.ApiException;
import com.zhexiao.convert.service.impl.ConvertServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "word生成接口")
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
    @ApiOperation(value = "上传json文件解析测试")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<Postman> upload(MultipartFile file){
        return convertService.upload(file);
    }

    @ApiOperation(value = "通过json文件生成word接口文档")
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Result<UploaderFileVO> writeWord(MultipartFile file, TableApiValDTO tableApiValDTO){
        try {
            return convertService.writeWord(file, tableApiValDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(e.getMessage());
        }
    }
}
