package com.zhexiao.convert.service;

import com.zhexiao.convert.base.Result;
import com.zhexiao.convert.entity.dto.TableApiValDTO;
import com.zhexiao.convert.entity.postman.Postman;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
public interface ConvertService {
    Result<Postman> upload(MultipartFile file);

    Result<UploaderFileVO> writeWord(MultipartFile file, TableApiValDTO tableApiValDTO) throws Exception;
}
