package com.cfs.obd2logger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ObdLogSummaryAvgDTO {

  private double speed;             // 속도
  private double rpm;               // RPM (엔진 분당 회전 속도)
  private double engineLoad;        // 엔진 부하 정보
  private double fuelLevel;         // 연료 정보 (퍼센트)
  private double throttlePos;       // 스로틀 위치 정보 (퍼센트)
  private double barometricPressure; // 공기압
  private double coolantTemp;       // 냉각수 온도
  private double distance;          // 거리
  private double runtime;
  private String vin;               // 차량 식별 번호
}

