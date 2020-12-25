package com.zhexiao.convert.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Auther: zhe.xiao
 * @Date: 2020/09/11
 * @Description
 */
@Data
@Accessors(chain = true)
public class UploaderFileVO {
    //文件的旧文件名
    private String originFilename;

    //文件的新文件名
    private String filename;

    //目的地
    private String dest;

    //保存到数据库的数据
    private String visitPath;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;
}
