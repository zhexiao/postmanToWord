package com.zhexiao.convert.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取文件数据，解析json文件
 *
 * @author zhe.xiao
 * @date 2020/09/11
 * @description 上传图片保存到本地
 */
@Component
public class ReadFile {
    private static final Logger log = LoggerFactory.getLogger(ReadFile.class);


    /**
     * 读取文件
     *
     * @param fis
     * @return
     */
    public String read(FileInputStream fis) {
        StringBuilder stringBuilder = new StringBuilder();

        try (
                FileChannel fisChannel = fis.getChannel()
        ) {
            //分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //读取数据
            int len = -1;
            while ((len = fisChannel.read(buffer)) != -1) {
                //上面把数据写入到了buffer，所以可知上面的buffer是写模式，调用flip把buffer切换到读模式，读取数据
                buffer.flip();

                byte[] bytes = buffer.array();
                stringBuilder.append(new String(bytes, 0, len));

                //读完了buffer，清空buffer
                buffer.clear();
            }
        } catch (Exception e) {
            log.info("读取文件异常,e={}", e.getMessage());
            return null;
        }

        return stringBuilder.toString();
    }

    /**
     * 解析文件
     *
     * @param file
     * @return
     */
    public String read(MultipartFile file) {
        try (
                FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        ) {
            return this.read(fileInputStream);
        } catch (Exception e) {
            log.info("读取文件异常,file={}, e={}", file, e.getMessage());
            return null;
        }
    }

    /**
     * 解析文件
     *
     * @param filepath
     * @return
     */
    public String read(String filepath) {
        try (
                FileInputStream fileInputStream = new FileInputStream(filepath);
        ) {
            return this.read(fileInputStream);
        } catch (Exception e) {
            log.info("读取文件异常,filepath={}, e={}", filepath, e.getMessage());
            return null;
        }
    }
}