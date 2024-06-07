package com.cfs.obd2logger.entity;

import com.cfs.obd2logger.dto.ObdLogDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(name = "BAROMETRIC_PRESSURE", nullable = false)
  private double barometricPressure; // 대기압

  @Column(name = "COOLANT_TEMP", nullable = false)
  private double coolantTemp;       // 냉각수 온도

  @Column(name = "THROTTLE_POS", nullable = false)
  private double throttlePos;       // 스로틀 위치 정보 (퍼센트)

  @Column(name = "RUN_TIME", nullable = false)
  private double runTime;           // 주행 시간

  @Column(name = "LON", nullable = false)
  private double lon;        // GPS 경도

  @Column(name = "LAT", nullable = false)
  private double lat;        // GPS 위도


  public ObdLogDTO toDTO() {
    return ObdLogDTO.builder()
        .deviceId(obdLogTablePK.getDeviceId())
        .timeStamp(obdLogTablePK.getTimeStamp())
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