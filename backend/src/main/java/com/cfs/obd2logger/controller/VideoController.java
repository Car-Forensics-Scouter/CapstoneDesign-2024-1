package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.VideoDTO;
import com.cfs.obd2logger.service.VideoService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

  private final VideoService videoService;

  /**
   * 파일 업로드 (Pre-signed URL 발급  방식) (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss) ex) 2020-11-11T12:34
   */
  @GetMapping("/URL-upload")
  public ResponseEntity<?> uploadUrlVideo(@RequestParam("deviceId") String deviceId,
      @RequestParam("fileName") String fileName, @RequestParam("extension") String extension,
      @RequestParam("createdDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime createdDate,
      @RequestParam("duration") int duration) {
    try {
      String uuidFileName = videoService.generateFileName(fileName) + "." + extension;
      String URL = videoService.uploadUrlVideo(deviceId, uuidFileName);
      videoService.handleAfterUrlUpload(deviceId, createdDate, duration, uuidFileName);
      return ResponseEntity.ok().body(URL);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 파일 업로드 (MultipartFile 방식) (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss) ex) 2020-11-11T12:34
   */
  @PostMapping("/upload")
  public ResponseEntity<?> uploadVideo(@RequestParam("video") MultipartFile video,
      @RequestParam("deviceId") String deviceId,
      @RequestParam("createdDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime createdDate,
      @RequestParam("duration") int duration) {
    try {
      // 동영상 업로드
      String videoName = video.getOriginalFilename();
      videoService.uploadVideo(video, deviceId);
      // 동영상 업로드 이후 작업 처리 (비동기)
      videoService.handleAfterUpload(video, deviceId, createdDate, duration, videoName);
      return ResponseEntity.ok().body(videoName);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 동영상 조회
   */
  @GetMapping("/find")
  public ResponseEntity<?> findVideo(@RequestParam("deviceId") String deviceId) {
    try {
      List<VideoDTO> videoDTOList = videoService.findAllVideo(deviceId);
      return ResponseEntity.ok().body(videoDTOList);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 동영상 다운로드
   */
  @GetMapping("/download")
  public ResponseEntity<?> downloadVideo(@RequestParam("deviceId") String deviceId,
      @RequestParam("videoName") String videoName) {
    try {
      String videoUrl = videoService.downloadVideo(deviceId, videoName);
      Map<String, String> response = new HashMap<>();
      response.put("url", videoUrl);
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 사용자의 전체 동영상 삭제
   */
  @GetMapping("/delete")
  public ResponseEntity<?> deleteVideo(@RequestParam("deviceId") String deviceId) {
    try {
      int deleted = videoService.deleteVideo(deviceId);
      return ResponseEntity.ok().body(deleted);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }
}
