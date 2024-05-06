package com.cfs.obd2logger.dto;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// TODO : HTTP API 통신에 맞춰 DTO 구현
@Getter
@Builder
@AllArgsConstructor
public class ObdLogDTO {

  private String deviceId;          // 라즈베리 파이 식별 번호
  private LocalDateTime timeStamp;  // 타임 스탬프
  private String vin;               // 차량 식별 번호
  private double speed;             // 속도
  private double rpm;               // RPM (엔진 분당 회전 속도)
  private double engineLoad;        // 엔진 부하 정보
  private double fuelLevel;         // 연료 정보 (퍼센트)
  private double oilTemp;           // 엔진오일 온도
  private double coolantTemp;       // 냉각수 온도
  private double throttlePos;       // 스로틀 위치 정보 (퍼센트)
  private double distance;          // 주행 거리
  private double runTime;           // 주행 시간
  private double runTimeMIL;        // 경고등 이후 엔진 작동 시간
  private double lat;               // GPS 위도
  private double lon;               // GPS 경도

  /**
   * DTO --> Entity 변환
   */
  public ObdLog toEntity() {
    ObdLogTablePK obdLogTablePK = new ObdLogTablePK(deviceId, timeStamp);
    return ObdLog.builder()
        .obdLogTablePK(obdLogTablePK)
        .vin(vin)
        .speed(speed)
        .rpm(rpm)
        .engineLoad(engineLoad)
        .fuelLevel(fuelLevel)
        .oilTemp(oilTemp)
        .coolantTemp(coolantTemp)
        .throttlePos(throttlePos)
        .distance(distance)
        .runTime(runTime)
        .runTimeMIL(runTimeMIL)
        .lat(lat)
        .lon(lon).build();
  }
}
