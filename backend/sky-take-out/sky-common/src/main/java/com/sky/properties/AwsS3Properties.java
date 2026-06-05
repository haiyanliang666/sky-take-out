package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aws.s3")
@Data
public class AwsS3Properties {

    private String region;
    private String bucketName;
    private String accessKeyId;
    private String secretAccessKey;
    private String publicUrl;
}
