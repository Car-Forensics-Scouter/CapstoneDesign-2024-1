package com.cfs.obd2logger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
// TODO 기본 키 넣기 : 복합 키 생성 or 인덱스를 기본 키로 사용 (의미x only 기본키 용도)
public class ObdLogEntity {


  @ManyToOne
  @JoinColumn(name = "deviceId")    // TODO 외래키 연결
  private String deviceId;          // 일련 번호

  private String vin;               // 차량 식별 번호
  // time_stamp                     // 타임 스탬프 TODO 타임 스탬프 속성
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
}
