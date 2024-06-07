package com.cfs.obd2logger.dto;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ObdLogDTO {

  private String deviceId;          // 라즈베리 파이 식별 번호
  private LocalDateTime timeStamp;  // 타임 스탬프 (yyyy-MM-ddTHH:mm:ss 포맷)
  private String vin;               // 차량 식별 번호
  private double speed;             // 속도
  private double rpm;               // RPM (엔진 분당 회전 속도)
  private double engineLoad;        // 엔진 부하 정보
  private double fuelLevel;         // 연료 정보 (퍼센트)
  private double barometricPressure; // 공기압
  private double coolantTemp;       // 냉각수 온도
  private double throttlePos;       // 스로틀 위치 정보 (퍼센트)
  private double runTime;           // 주행 시간
  private double lon;               // GPS 경도
  private double lat;               // GPS 위도

  /**
   * DTO --> Entity 변환
   */
  public ObdLog toEntity() {
    ObdLogTablePK obdLogTablePK = ObdLogTablePK.builder()
        .deviceId(deviceId)
        .timeStamp(timeStamp).build();
    return ObdLog.builder()
        .obdLogTablePK(obdLogTablePK)
        .vin(vin)
        .speed(speed)
        .rpm(rpm)
        .engineLoad(engineLoad)
        .fuelLevel(fuelLevel)
        .barometricPressure(barometricPressure)
        .coolantTemp(coolantTemp)
        .throttlePos(throttlePos)
        .runTime(runTime)
        .lat(lat)
        .lon(lon).build();
  }
}
