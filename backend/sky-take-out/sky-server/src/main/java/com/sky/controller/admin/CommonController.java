package com.sky.controller.admin;


import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.sky.result.Result;
import com.sky.utils.AwsS3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common API")
@Slf4j
public class CommonController {

    private final AwsS3Util awsS3Util;

    public CommonController(AwsS3Util awsS3Util) {
        this.awsS3Util = awsS3Util;
    }

    /** AWS S3
     * File upload
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("File Upload")
    public Result<String> upload(MultipartFile file){
        log.info("File Upload:{}, size:{}, contentType:{}", file.getOriginalFilename(), file.getSize(), file.getContentType());

        try {
            String objectName = buildObjectName(file.getOriginalFilename());
            String url = awsS3Util.upload(file.getBytes(), objectName, file.getContentType());
            return Result.success(url);
        } catch (AmazonS3Exception e) {
            log.error("S3 file upload failed: {}", e.getErrorMessage(), e);
            return Result.error("S3文件上传失败：" + e.getErrorCode());
        } catch (IOException e) {
            log.error("File upload failed", e);
            return Result.error("文件上传失败");
        } catch (Exception e) {
            log.error("File upload failed", e);
            return Result.error("文件上传失败");
        }
    }

    private String buildObjectName(String originalFilename) {
        String extension = "";
        if (originalFilename != null) {
            int index = originalFilename.lastIndexOf(".");
            if (index >= 0) {
                extension = originalFilename.substring(index);
            }
        }
        return "images/" + UUID.randomUUID() + extension;
    }


//    /** Aliposs
//     * 文件上传
//     * @param file
//     * @return
//     */
//    @PostMapping("/upload")
//    @ApiOperation("文件上传")
//    public Result<String> upload(MultipartFile file){
//        log.info("文件上传：{}",file);
//
//        try {
//            //原始文件名
//            String originalFilename = file.getOriginalFilename();
//            //截取原始文件名的后缀   dfdfdf.png
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            //构造新文件名称
//            String objectName = UUID.randomUUID().toString() + extension;
//
//            //文件的请求路径
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//            return Result.success(filePath);
//        } catch (IOException e) {
//            log.error("文件上传失败：{}", e);
//        }
//
//        return Result.error(MessageConstant.UPLOAD_FAILED);
//    }


}
