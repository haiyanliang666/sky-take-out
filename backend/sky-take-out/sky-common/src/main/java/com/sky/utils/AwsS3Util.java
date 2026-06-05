package com.sky.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sky.properties.AwsS3Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class AwsS3Util {

    private final AwsS3Properties awsS3Properties;

    public AwsS3Util(AwsS3Properties awsS3Properties) {
        this.awsS3Properties = awsS3Properties;
    }

    public String upload(byte[] bytes, String objectName, String contentType) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
                awsS3Properties.getAccessKeyId(),
                awsS3Properties.getSecretAccessKey());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(awsS3Properties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        if (contentType != null && !contentType.isEmpty()) {
            metadata.setContentType(contentType);
        }

        s3Client.putObject(new PutObjectRequest(
                awsS3Properties.getBucketName(),
                objectName,
                new ByteArrayInputStream(bytes),
                metadata));

        String url = buildPublicUrl(objectName);
        log.info("文件上传到:{}", url);
        return url;
    }

    private String buildPublicUrl(String objectName) {
        String publicUrl = awsS3Properties.getPublicUrl();
        if (publicUrl != null && !publicUrl.trim().isEmpty()) {
            return publicUrl.replaceAll("/$", "") + "/" + objectName;
        }
        return "https://" + awsS3Properties.getBucketName() + ".s3." + awsS3Properties.getRegion()
                + ".amazonaws.com/" + objectName;
    }
}
