package com.zhexiao.convert.controller;

import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import com.zhexiao.convert.utils.file.FileUploaderUtil;
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
    FileUploaderUtil fileUploaderUtil;

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Result<UploaderFileVO> convert(MultipartFile file){
        UploaderFileVO fileVO = fileUploaderUtil.upload(file);
        return Result.success(fileVO);
    }

    @RequestMapping(value = "/writeWord", method = RequestMethod.POST)
    public Result<String> writeWord(){

        return Result.success("ok");
    }
}
