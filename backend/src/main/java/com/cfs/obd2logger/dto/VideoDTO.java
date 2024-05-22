package com.cfs.obd2logger.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VideoDTO {

  private String deviceId;                      // 라즈베리파이 식별번호 (외래키: UserEntity)
  private String filePath;                      // 동영상 파일 경로
  private String thumbnail;                      // 썸네일 파일 경로
  private String title;                         // 동영상 이름
  private int duration;                         // 동영상 길이
  private LocalDateTime createdDate;             // 생성 날짜
  private LocalDateTime endDate;                // 녹화 종료 날짜
}