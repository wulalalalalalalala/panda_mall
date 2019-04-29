package com.panda.upload.controller;

import com.panda.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * - 请求方式：上传肯定是POST
     * - 请求路径：/upload/image
     * - 请求参数：文件，参数名是file，SpringMVC会封装为一个接口：MultipartFile
     * - 返回结果：上传成功后得到的文件的url路径
     *
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = this.uploadService.uploadImage(file);
        //url为空，说明上传失败
        if (StringUtils.isBlank(url)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //上传成功并返回url地址
        return ResponseEntity.ok(url);
    }
}
