package com.fourback.runus.global.amazon.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * packageName    : com.fourback.runus.global.amazon.config
 * fileName       : S3Config
 * author         : 김은정
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        김은정            최초 생성
 */
@Log4j2
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;


    @Bean
    public AmazonS3 getAmazonS3() {
        log.info("====>>>>>>>>>> getAmazonS3()");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return  AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .enablePathStyleAccess()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }
}
