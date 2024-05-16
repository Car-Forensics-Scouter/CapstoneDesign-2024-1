package com.cfs.obd2logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ObdLogDataRepositoryTest {

  @Autowired
  ObdLogDataRepository obdLogDataRepository;

  @Test
  public void testSelectLogByDeviceidAndDay() {
    // VF190913 유저의 2024.04.01 로그1
    LocalDateTime localDateTime1 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    ObdLogTablePK obdLogTablePK1 = ObdLogTablePK.builder()
        .deviceId("VF190913")
        .timeStamp(localDateTime1).build();
    ObdLog obdLog1 = ObdLog.builder()
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("A12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .oilTemp(1.0)
        .runTime(1.0)
        .runTimeMIL(1.0)
        .throttlePos(1.0).build();

    // VF190913 유저의 2024.04.01 로그2
    LocalDateTime localDateTime2 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    ObdLogTablePK obdLogTablePK2 = ObdLogTablePK.builder()
        .deviceId("VF190913")
        .timeStamp(localDateTime1).build();

    ObdLog obdLog2 = ObdLog.builder()
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("A12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .oilTemp(1.0)
        .runTime(1.0)
        .runTimeMIL(1.0)
        .throttlePos(1.0).build();

    // HD190913 유저의 2024.04.01 로그1
    LocalDateTime localDateTime3 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    ObdLogTablePK obdLogTablePK3 = ObdLogTablePK.builder()
        .deviceId("HD190913")
        .timeStamp(localDateTime3).build();

    ObdLog obdLog3 = ObdLog.builder()
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .oilTemp(1.0)
        .runTime(1.0)
        .runTimeMIL(1.0)
        .throttlePos(1.0).build();

    // HD190913 유저의 2024.04.01 로그2
    LocalDateTime localDateTime4 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    ObdLogTablePK obdLogTablePK4 = ObdLogTablePK.builder()
        .deviceId("HD190913")
        .timeStamp(localDateTime4).build();

    ObdLog obdLog4 = ObdLog.builder()
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .oilTemp(1.0)
        .runTime(1.0)
        .runTimeMIL(1.0)
        .throttlePos(1.0).build();

    // HD190913 유저의 2024.04.11 로그1
    LocalDateTime localDateTime5 = LocalDateTime.of(2024, 04, 01, 17, 15, 12);
    ObdLogTablePK obdLogTablePK5 = ObdLogTablePK.builder()
        .deviceId("HD190913")
        .timeStamp(localDateTime5).build();

    ObdLog obdLog5 = ObdLog.builder()
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .oilTemp(1.0)
        .runTime(1.0)
        .runTimeMIL(1.0)
        .throttlePos(1.0).build();

    obdLogDataRepository.save(obdLog1);
    obdLogDataRepository.save(obdLog2);
    obdLogDataRepository.save(obdLog3);
    obdLogDataRepository.save(obdLog4);
    obdLogDataRepository.save(obdLog5);

    String deviceId1 = "VF190913";
    String deviceId2 = "HD190913";

    LocalDateTime start240401 = LocalDateTime.of(2024, 04, 01, 00, 00, 00);
    LocalDateTime end240401 = LocalDateTime.of(2024, 04, 01, 23, 59, 59);

    LocalDateTime start240411 = LocalDateTime.of(2024, 04, 11, 00, 00, 00);
    LocalDateTime end240411 = LocalDateTime.of(2024, 04, 11, 23, 59, 59);

    // deviceId1의 240401 날짜의 모든 로그 출력 (expected: 2)
    List<ObdLog> dvId01and240401 = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId1,
        start240401, end240401);

    // deviceId2의 240401 날짜의 모든 로그 출력 (expected: 2)
    List<ObdLog> dvId02and240401 = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId1,
        start240401, end240401);

    // 240411 날짜의 모든 로그 출력 (expected: 1)
    List<ObdLog> dvId02and240411 = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId2,
        start240411, end240411);

    assertThat(dvId01and240401.size()).isEqualTo(2);    // deviceId01 의 240401 날짜 로그가 2개가 아니라면, 오류
    assertThat(dvId02and240401.size()).isEqualTo(2);    // deviceId02 의 240401 날짜 로그가 2개가 아니라면, 오류
    assertThat(dvId02and240411.size()).isEqualTo(1);    // deviceId02 의 240411 날짜 로그가 1개가 아니라면, 오류
  }
}