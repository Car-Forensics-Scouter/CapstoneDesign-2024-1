package com.cfs.obd2logger.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
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
    TransferManager transferManager = TransferManagerBuilder.standard()
        .withS3Client(amazonS3Client).build();

    // 메타 데이터 설정
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());              // 콘텐츠 길이 설정
    metadata.setContentType(file.getContentType());

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
    // 메타 데이터 설정
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(uploadFile.length());

    try (InputStream inputStream = new FileInputStream(uploadFile)) {
      amazonS3Client.putObject(bucket, fileName, inputStream, metadata);
      return amazonS3Client.getUrl(bucket, fileName).toString();
    } catch (IOException e) {
      return e.toString();
    }
  }


  /**
   * 파일 다운로드 (URL)
   */
  public String downloadFile(String filePath) {
    if (!amazonS3Client.doesObjectExist(bucket, filePath)) {
      return "No Exists";
    }
    return generateGetURL(filePath, 60 * 24);
  }

  /**
   * AWS Pre-signed URL 생성
   */
  public String generateGetURL(String filePath, int minutes) {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * (long) minutes;           // Access 만료 기한 1일
    expiration.setTime(expTimeMillis);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, filePath)
            .withMethod(HttpMethod.GET)           // 파일 다운로드 URL (GET)
            .withExpiration(expiration);
    return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
  }

  /**
   * AWS Pre-signed URL 생성
   */
  public String generatePutURL(String filePath, int minutes) {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * (long) minutes;           // Access 만료 기한 1일
    expiration.setTime(expTimeMillis);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, filePath)
            .withMethod(HttpMethod.PUT)           // 파일 다운로드 URL (GET)
            .withExpiration(expiration);
    return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
  }

  /**
   * 유저의 모든 동영상 삭제 (폴더 삭제)
   */
  public void deleteFolder(String deviceId) {
    ObjectListing objectList = amazonS3Client.listObjects(bucket, deviceId);
    List<S3ObjectSummary> objectSummeryList = objectList.getObjectSummaries();
    String[] keysList = new String[objectSummeryList.size()];
    int count = 0;
    for (S3ObjectSummary summery : objectSummeryList) {
      keysList[count++] = summery.getKey();
    }
    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket).withKeys(
        keysList);
    amazonS3Client.deleteObjects(deleteObjectsRequest);
  }
}