package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "VIDEO")
@NoArgsConstructor
@AllArgsConstructor
public class Video {

  @Id
  @Column(name = "DEVICE_ID", length = 8, nullable = false)
  private String deviceId;        // 라즈베리파이 식별번호

  @Column(name = "FILE_PATH", length = 512, nullable = false)
  private String filePath;                      // 동영상 파일 경로

  @Column(name = "THUMBNAIL", length = 512, nullable = false)
  private String thumbnail;                      // 썸네일 파일 경로

  @Column(name = "TITLE", length = 40, nullable = false)
  private String title;                         // 동영상 이름

  @Column(name = "DURATION", nullable = false)
  private int duration;                         // 동영상 길이

  @Column(name = "DATE")
  private LocalDateTime date;          // 업로드 날짜
}