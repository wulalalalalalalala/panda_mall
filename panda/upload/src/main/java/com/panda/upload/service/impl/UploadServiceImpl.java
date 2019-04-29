package com.panda.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.panda.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class UploadServiceImpl implements UploadService {
    //日志
    Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadImage(MultipartFile file) {

        try {
            Set<String> ext = new HashSet<>();
            ext.add("image/jpeg");
            ext.add("image/png");

            //校验文件类型
            String contentType = file.getContentType();
            if (ext.contains(contentType)) {
                logger.info("文件类型不匹配，{}，请校验", contentType);
            }

            //校验文件内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read == null) {
                logger.info("文件内容不匹配请校验", contentType);
            }

            //获取上传的文件的名称
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀名
            String extension = StringUtils.substringAfterLast(originalFilename, ".");
            //实现文件上传逻辑 参数：文件输入流，文件大小，文件后缀名
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //返回文件上传后的地址
            //需要解析image.panda.com到192.168.206.66
            return "http://image.panda.com/" + storePath.getFullPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
