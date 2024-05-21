package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.VideoDTO;
import com.cfs.obd2logger.entity.Video;
import com.cfs.obd2logger.repository.VideoRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
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
  public void saveVideo(String deviceId, String fileName, LocalDateTime createdDate,
      int duration) {
    // TODO : 인증된 유저인지 검사, User 넣어주기
    try {
      // 동영상 엔티티 생성
      Video video = Video.builder()
          .deviceId(deviceId)
          .title(fileName)
          .filePath(deviceId + "/" + fileName)
          .date(createdDate)
          .duration(duration)
          .thumbnail("sampleThumb").build();
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
  public String uploadVideo(MultipartFile file, String deviceId) throws Exception {
    return s3Service.uploadMultiFile(file, deviceId);
  }

  /**
   * 동영상 파일 업로드 -> 응답 -> 썸네일 생성 -> db 저장, 썸네일 s3 업로드
   */
  @Async
  public void handleAfterUpload(MultipartFile file, String deviceId, LocalDateTime createdDate,
      int duration, String fileName) throws IOException {
    // 썸네일 생성
    File thumbnail = thumbnailService.generateVideoThumbnail(file,
        fileName);
    // 썸네일 업로드
    String thumbnailURL = thumbnailService.uploadThumbnail(thumbnail, fileName,
        deviceId);
    // Video 엔티티 생성
    Video video = Video.builder()
        .deviceId(deviceId)
        .title(fileName)
        .filePath(deviceId + "/" + fileName)
        .date(createdDate)
        .duration(duration)
        .thumbnail(thumbnailURL).build();
    // DB에 Video 저장
    videoRepository.save(video);
  }

  /**
   * videoEntity 리스트 --> videoDTO 리스트
   */
  private List<VideoDTO> ListEntityToListDTO(List<Video> videoList) {
    return videoList.stream()
        .map(Video::toDTO)
        .collect(Collectors.toList());
  }
}
