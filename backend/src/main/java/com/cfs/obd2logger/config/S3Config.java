package com.cfs.obd2logger.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class S3Config {

  @Value("${cloud.aws.region.static}")
  private String region;
  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;
  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // AWS S3 Client 생성
  @Bean
  public AmazonS3Client amazonS3() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    return (AmazonS3Client) AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }
}
