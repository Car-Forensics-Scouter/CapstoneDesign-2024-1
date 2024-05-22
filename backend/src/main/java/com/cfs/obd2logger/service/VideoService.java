package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.VideoDTO;
import com.cfs.obd2logger.entity.Video;
import com.cfs.obd2logger.repository.VideoRepository;
import jakarta.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoService {

  @Autowired
  private VideoRepository videoRepository;

  @Autowired
  private S3Service s3Service;

  @Autowired
  private ThumbnailService thumbnailService;

  /**
   * 동영상 DB에 동영상 정보 저장
   */
  public void saveVideo(String deviceId, @Nullable String thumbnail, String fileName,
      int duration, LocalDateTime createdDate) {
    try {
      LocalDateTime endDate = createdDate.withSecond(duration);
      // 동영상 엔티티 생성
      Video video = Video.builder()
          .deviceId(deviceId)
          .title(fileName)
          .filePath(deviceId + "/" + fileName)
          .createdDate(createdDate)
          .endDate(endDate)
          .duration(duration)
          .thumbnail(thumbnail).build();
      videoRepository.save(video);
    } catch (Exception e) {
      // 예외 처리
    }
  }

  /**
   * 사용자의 비디오 조회
   */
  public Video findVideo(String deviceId, String title) {
    return videoRepository.findVideoByDeviceIdAndTitle(deviceId, title);
  }

  /**
   * 사용자의 모든 동영상 조회
   */
  public List<VideoDTO> findAllVideo(String deviceId) {
    List<Video> videoList = videoRepository.findAllByDeviceId(deviceId);
    return ListEntityToListDTO(videoList);
  }

  /**
   * 사용자의 모든 동영상 삭제
   */
  public int deleteVideo(String deviceId) {
    return videoRepository.deleteAllByDeviceId(deviceId);
  }

  /**
   * 동영상 업로드
   */
  public String uploadUrlVideo(String deviceId, String fileName) {
    String filePath = deviceId + "/" + fileName;
    return s3Service.generatePutURL(filePath, 60 * 24);       // 24시간 유효 URL 발급
  }

  /**
   * 동영상 업로드
   */
  public String uploadVideo(MultipartFile file, String deviceId) throws Exception {
    return s3Service.uploadMultiFile(file, deviceId);
    // TODO : 동영상 처리
  }

  /**
   * 동영상 다운로드
   */
  public String downloadVideo(String deviceId, String fileName) {
    return s3Service.downloadFile(deviceId + "/" + fileName);
  }

  /**
   * 동영상 URL에 라즈베리파이 업로드, 서버는 DB에 동영상 정보(filePath) 저장
   */
  @Async
  public void handleAfterUrlUpload(String deviceId, LocalDateTime createdDate,
      int duration, String fileName) {
    saveVideo(deviceId, null, fileName, duration, createdDate);
    // 썸네일 처리
  }

  /**
   * 동영상 파일 업로드 -> 응답 -> 썸네일 생성 -> db 저장, 썸네일 s3 업로드
   */
  @Async
  public void handleAfterUpload(MultipartFile file, String deviceId, LocalDateTime createdDate,
      int duration, String fileName) throws IOException {
    System.out.println("START THUMBNAIL GENERATE");
    // 썸네일 생성
    File thumbnail = thumbnailService.generateVideoThumbnail(file,
        fileName);
    System.out.println("START THUMBNAIL UPLOAD");
    // 썸네일 업로드
    String thumbnailURL = thumbnailService.uploadThumbnail(thumbnail, fileName,
        deviceId);
    System.out.println("START VIDEO SAVE");
    // DB에 비디오 저장
    saveVideo(deviceId, thumbnailURL, fileName, duration, createdDate);
  }

  /**
   * videoEntity 리스트 --> videoDTO 리스트
   */
  private List<VideoDTO> ListEntityToListDTO(List<Video> videoList) {
    return videoList.stream()
        .map(Video::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * 파일 제목+UUID 생성
   */
  public String generateFileName(String fileName) {
    return fileName + "_" + UUID.randomUUID();
  }
}
