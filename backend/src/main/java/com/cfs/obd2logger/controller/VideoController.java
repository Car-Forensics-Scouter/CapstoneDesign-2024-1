package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.VideoDTO;
import com.cfs.obd2logger.service.VideoService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private final VideoService videoService;

  /**
   * 파일 업로드 (MultipartFile 방식)
   */
  @PostMapping("/upload")
  public ResponseEntity<?> uploadVideo(@RequestParam("video") MultipartFile video,
      @RequestParam("deviceId") String deviceId,
      @RequestParam("createdDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdDate,
      @RequestParam("duration") int duration)
      throws IOException {
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
}
