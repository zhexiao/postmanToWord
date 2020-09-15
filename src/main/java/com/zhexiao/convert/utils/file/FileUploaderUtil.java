package com.zhexiao.convert.utils.file;


import com.zhexiao.convert.entity.vo.UploaderFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 * 通过NIO保存图片数据
 *
 * @Auther: zhe.xiao
 * @Date: 2020/09/11
 * @Description 上传图片保存到本地
 */
@Component
public class FileUploaderUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUploaderUtil.class);

    /**
     * 文件上传的保存路径
     */
    @Value("${app.fileDest}")
    private String UPLOAD_PATH;

    /**
     * HTTP文件访问前缀
     */
    @Value("${app.filePathRoute}")
    private String filePathRoute;


    /**
     * 图片保存的公共路径
     *
     * 注意：
     */
    public String VisitPath = "file:" + UPLOAD_PATH + "/";


    /**
     * 根据文件老名字得到新名字
     *
     * @param oldName
     * @return
     */
    public String getNewFilename(String oldName) {
        String suffix = FileUtils.getFileExt(oldName);
        return System.currentTimeMillis() +"_"+ new Random().nextInt(10000) + "." + suffix;
    }

    /**
     * 保存文件
     * @return
     */
    public String save(MultipartFile file, String outputPath) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        //创建通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(outputPath);

            //读取channel
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //通道间传输数据
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }finally {
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (fis != null){
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if(fos != null){
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return outputPath;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public UploaderFileVO upload(MultipartFile file){
        String newFilename = getNewFilename(file.getOriginalFilename());
        log.info("newFilename="+newFilename);

        return upload(file, this.UPLOAD_PATH, newFilename);
    }

    /**
     * 文件上传
     * @param file
     * @param outputPath 路径 F:\workspace\3dp-backend-server\Downloads
     * @param filename 文件名
     * @return
     */
    public UploaderFileVO upload(MultipartFile file, String outputPath, String filename){
        String filepath = outputPath + "/" + filename;
        log.info("filepath="+filepath);


        UploaderFileVO fileVO = new UploaderFileVO()
                .setDest(this.UPLOAD_PATH)
                .setOriginFilename(file.getOriginalFilename())
                .setFilename(filename)
                .setFullpath(outputPath)
                .setVisitPath(filePathRoute + filename)
                .setCreateDatetime(LocalDateTime.now());

        log.info("fileVO="+fileVO.toString());
        try {
            save(file, filepath);
        } catch (Exception e) {
            log.info("e="+e.getMessage());
        }
        return fileVO;
    }
}
