package com.cfs.obd2logger.entity;

import com.cfs.obd2logger.dto.VideoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString   // TODO 테스트 후 삭제
@Entity
@Getter
@Setter
@Builder
@Table(name = "VIDEO")
@NoArgsConstructor
@AllArgsConstructor
public class Video {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "DEVICE_ID", referencedColumnName = "DEVICE_ID")
  private UserEntity user;

  @Column(name = "FILE_PATH", length = 512, nullable = false)
  private String filePath;                      // 동영상 파일 경로

  @Column(name = "THUMBNAIL", length = 512, nullable = true)
  private String thumbnail;                      // 썸네일 파일 경로

  @Id
  @Column(name = "TITLE", length = 256, nullable = false)
  private String title;                         // 동영상 이름

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;          // 생성 날짜

  @Column(name = "END_DATE", nullable = false)
  private LocalDateTime endDate;          // 녹화 종료 날짜

  public VideoDTO toDTO() {
    return VideoDTO.builder()
        .deviceId(user.getDeviceId())
        .thumbnail(thumbnail)          // 썸네일 파일 경로
        .title(title)                 // 동영상 이름
        .createdDate(createdDate)            // 생성 날짜
        .endDate(endDate).build();       // 녹화 종료 날짜
  }
}
