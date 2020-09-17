package com.zhexiao.convert.utils.json;

import com.zhexiao.convert.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 解析json文件
 *
 * @Auther: zhe.xiao
 * @Date: 2020/09/16
 * @Description
 */
@Component
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 解析文件
     * @param filepath
     * @return
     */
    public String read(String filepath) {
        FileInputStream fileInputStream = null;
        String res = "";

        try {
            fileInputStream = new FileInputStream(filepath);
            res = read(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    /**
     * 解析文件
     * @param file
     * @return
     */
    public String read(MultipartFile file) {
        FileInputStream fileInputStream = null;
        String res = "";

        try {
            fileInputStream = (FileInputStream) file.getInputStream();
            res = read(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(e.getMessage());
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    /**
     * 读取文件
     * @param fis
     * @return
     */
    public String read(FileInputStream fis){
        StringBuilder stringBuilder = null;
        FileChannel fisChannel = null;

        try {
            stringBuilder = new StringBuilder();
            fisChannel = fis.getChannel();

            //分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int len = -1;
            while ((len = fisChannel.read(buffer)) != -1){
                //上面把数据写入到了buffer，所以可知上面的buffer是写模式

                //调用flip把buffer切换到读模式，读取数据
                buffer.flip();
                byte[] bytes = buffer.array();
                stringBuilder.append(new String(bytes, 0, len));

                //读完了buffer，清空buffer
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("————读取文件失败!————");
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(fisChannel != null){
                try {
                    fisChannel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            logger.info("————读取文件结束!————");
        }

        if( stringBuilder != null ){
            return stringBuilder.toString();
        }

        return "";
    }
}