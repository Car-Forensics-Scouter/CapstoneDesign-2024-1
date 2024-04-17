package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class ObdLog {

  @EmbeddedId
  private ObdLogTablePK obdLogTablePK;      // 라즈베리 파이 식별 번호, 타임 스탬프

  @Column(name = "VIN", length = 20, nullable = false)
  private String vin;               // 차량 식별 번호

  @Column(name = "SPEED", nullable = false)
  private double speed;             // 속도

  @Column(name = "RPM", nullable = false)
  private double rpm;               // RPM (엔진 분당 회전 속도)

  @Column(name = "ENINGE_LOAD", nullable = false)
  private double engineLoad;        // 엔진 부하 정보

  @Column(name = "FUEL_LEVEL", nullable = false)
  private double fuelLevel;         // 연료 정보 (퍼센트)

  @Column(name = "OIL_TEMP", nullable = false)
  private double oilTemp;           // 엔진오일 온도

  @Column(name = "COOLANT_TEMP", nullable = false)
  private double coolantTemp;       // 냉각수 온도

  @Column(name = "THROTTLE_POS", nullable = false)
  private double throttlePos;       // 스로틀 위치 정보 (퍼센트)

  @Column(name = "DISTANCE", nullable = false)
  private double distance;          // 주행 거리

  @Column(name = "RUN_TIME", nullable = false)
  private double runTime;           // 주행 시간

  @Column(name = "RUN_TIME_MIL", nullable = false)
  private double runTimeMIL;        // 경고등 이후 엔진 작동 시간
}