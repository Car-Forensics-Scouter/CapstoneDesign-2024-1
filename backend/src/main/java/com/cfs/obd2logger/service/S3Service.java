package com.cfs.obd2logger.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Autowired
  private AmazonS3Client amazonS3Client;


  /**
   * 파일 업로드 (MultipartFile 방식) 후 파일의 퍼블릭 URL 반환
   */
  public String uploadMultiFile(MultipartFile file, String deviceId) throws IOException {
    // 메타 데이터 설정
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());              // 콘텐츠 길이 설정
    metadata.setContentType(file.getContentType());

    // String fileName = generateFileName(file, deviceId);     // 파일명 생성
    String fileName = file.getOriginalFilename();            // 파일명 획득
    String dir = deviceId + "/";                            // 디렉토리+파일명
    try (InputStream inputStream = file.getInputStream()) {
      amazonS3Client.putObject(bucket, dir + fileName, inputStream, metadata);
      return amazonS3Client.getUrl(bucket, fileName).toString();      // 퍼블릭 URL 반환
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * 파일 업로드 (File 방식) 후 URL 반환
   */
  public String uploadFile(File uploadFile, String fileName) {
    amazonS3Client.putObject(bucket, fileName, uploadFile);
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }


  /**
   * 파일 다운로드 (URL)
   */
  public String downloadFile(String filePath) {
    if (!amazonS3Client.doesObjectExist(bucket, filePath)) {
      return "No Exists";
    }
    return generateURL(filePath);
  }

  /**
   * AWS Pre-signed URL 생성
   */
  private String generateURL(String filePath) {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * 60 * 24;           // Access 만료 기한 1일
    expiration.setTime(expTimeMillis);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, filePath)
            .withMethod(HttpMethod.GET)           // 파일 다운로드 URL (GET)
            .withExpiration(expiration);
    return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
  }

//  /**
//   * 파일 제목+UUID 생성
//   */
//  private String generateFileName(MultipartFile file, String deviceId) {
//    return file.getOriginalFilename() + "_" + UUID.randomUUID();
//  }
}