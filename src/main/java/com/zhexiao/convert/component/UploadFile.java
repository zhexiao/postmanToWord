package com.zhexiao.convert.component;


import com.zhexiao.convert.config.AppConfig;
import com.zhexiao.convert.entity.vo.UploaderFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 通过NIO保存图片数据
 *
 * @author zhe.xiao
 * @date 2020/09/11
 * @description 上传图片保存到本地
 */
@Component
public class UploadFile {

    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    @Autowired
    AppConfig appConfig;

    /**
     * 生成随机文件名
     * <p>
     * 不要加后缀，加了后缀后会造成文件被额外的进程使用
     *
     * @return
     */
    public static String generateFilename(String filename, boolean usingSuffix) {
        String suffix = "tmp";
        if (filename != null) {
            suffix = getFileSuffix(filename);
        }

        String randomName = UUID.randomUUID().toString().replace("-", "");
        if (usingSuffix) {
            randomName += "." + suffix;
        }

        logger.info("tmpName={}, suffix={}", randomName, suffix);
        return randomName;
    }

    /**
     * 上传单个文件
     *
     * @param file       文件的句柄
     * @param outputPath 输出的最终文件地址
     * @param filename   文件名称
     * @return
     */
    public UploaderFileVO saveFile(MultipartFile file, String outputPath, String filename) {
        if (null == file) {
            logger.info("file数据为null");
            return null;
        }

        File outputDir = new File(outputPath);
        if (!outputDir.exists() && !outputDir.isDirectory()) {
            outputDir.mkdir();
            logger.info("FileUploadVO 创建目录 outputDir={}", outputDir);
        }

        String dest = outputPath + File.separator + filename;
        logger.info("FileUploadVO upload outputPath={}, filename={}, dest={}", outputPath, filename, dest);

        try (
                FileInputStream fis = (FileInputStream) file.getInputStream();
                FileOutputStream fos = new FileOutputStream(dest);
                FileChannel inChannel = fis.getChannel();
                FileChannel outChannel = fos.getChannel();
        ) {
            //通道间传输数据
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            logger.info("saveFile 保存文件失败,e={}", e.getMessage());
            return null;
        }

        //返回数据
        UploaderFileVO fileVO = new UploaderFileVO()
                .setOriginFilename(file.getOriginalFilename())
                .setFilename(filename)
                .setDest(dest)
                .setVisitPath(appConfig.getFileVisitHost() + appConfig.getFilePathRoute() + filename)
                .setCreateDatetime(LocalDateTime.now());

        logger.info("upload result={}", fileVO);
        return fileVO;
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public UploaderFileVO upload(MultipartFile file) {
        if (null == file) {
            logger.info("file为null");
            return null;
        }

        //生成随机文件名
        String filename = generateFilename(file.getOriginalFilename(), true);
        return this.saveFile(file, appConfig.getFileDest(), filename);
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @param filename 文件名
     * @return
     */
    public UploaderFileVO upload(MultipartFile file, String filename) {
        return this.saveFile(file, appConfig.getFileDest(), filename);
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @param dirName  子目录名称
     * @param filename 文件名，为空则自动生成随机名
     * @return
     */
    public UploaderFileVO upload(MultipartFile file, String dirName, String filename) {
        //保存文件
        String outputPath = appConfig.getFileDest() + File.separator + dirName;

        //如果文件名为空，则使用随机生成
        if (StringUtils.isEmpty(filename)) {
            filename = generateFilename(file.getOriginalFilename(), true);
        }
        return this.saveFile(file, outputPath, filename);
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @param dirName         子目录名称
     * @param filename        文件名
     * @param removeDuplicate 是否删除同名文件
     * @return
     */
    public UploaderFileVO upload(MultipartFile file, String dirName, String filename, boolean removeDuplicate) {
        //保存文件
        String outputPath = appConfig.getFileDest() + File.separator + dirName;
        String dest = outputPath + File.separator + filename;

        //检查文件并删除
        LinkOption[] linkOptions = new LinkOption[1];
        linkOptions[0] = LinkOption.NOFOLLOW_LINKS;

        boolean exists = Files.exists(Paths.get(dest), linkOptions);
        if (exists && removeDuplicate) {
            try {
                Files.delete(Paths.get(dest));
            } catch (IOException e) {
                logger.info("文件删除失败,e={}", e.getMessage());
            }

            logger.info("文件存在,删除了旧文件={}", dest);
        }

        return this.saveFile(file, outputPath, filename);
    }

    /**
     * 返回文件的后缀名
     *
     * @param filename
     * @return
     */
    private static String getFileSuffix(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
