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
    ObdLog obdLog1 = new ObdLog();
    ObdLogTablePK obdLogTablePK1 = new ObdLogTablePK();
    LocalDateTime localDateTime1 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    obdLog1.setDistance(1.0);
    obdLog1.setRpm(1.0);
    obdLog1.setCoolantTemp(1.0);
    obdLog1.setSpeed(1.0);
    obdLog1.setVin("A12345");
    obdLog1.setEngineLoad(1.0);
    obdLog1.setFuelLevel(1.0);
    obdLog1.setOilTemp(1.0);
    obdLog1.setRunTime(1.0);
    obdLog1.setRunTimeMIL(1.0);
    obdLog1.setThrottlePos(1.0);
    obdLogTablePK1.setDeviceId("VF190913");
    obdLogTablePK1.setTimeStamp(localDateTime1);
    obdLog1.setObdLogTablePK(obdLogTablePK1);

    // VF190913 유저의 2024.04.01 로그2
    ObdLog obdLog2 = new ObdLog();
    ObdLogTablePK obdLogTablePK2 = new ObdLogTablePK();
    LocalDateTime localDateTime2 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    obdLog2.setDistance(1.0);
    obdLog2.setRpm(1.0);
    obdLog2.setCoolantTemp(1.0);
    obdLog2.setSpeed(1.0);
    obdLog2.setVin("A12345");
    obdLog2.setEngineLoad(1.0);
    obdLog2.setFuelLevel(1.0);
    obdLog2.setOilTemp(1.0);
    obdLog2.setRunTime(1.0);
    obdLog2.setRunTimeMIL(1.0);
    obdLog2.setThrottlePos(1.0);
    obdLogTablePK2.setDeviceId("VF190913");
    obdLogTablePK2.setTimeStamp(localDateTime2);
    obdLog2.setObdLogTablePK(obdLogTablePK2);

    // HD190913 유저의 2024.04.01 로그1
    ObdLog obdLog3 = new ObdLog();
    ObdLogTablePK obdLogTablePK3 = new ObdLogTablePK();
    LocalDateTime localDateTime3 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    obdLog3.setDistance(1.0);
    obdLog3.setRpm(1.0);
    obdLog3.setCoolantTemp(1.0);
    obdLog3.setSpeed(1.0);
    obdLog3.setVin("A12345");
    obdLog3.setEngineLoad(1.0);
    obdLog3.setFuelLevel(1.0);
    obdLog3.setOilTemp(1.0);
    obdLog3.setRunTime(1.0);
    obdLog3.setRunTimeMIL(1.0);
    obdLog3.setThrottlePos(1.0);
    obdLogTablePK3.setDeviceId("HD190913");
    obdLogTablePK3.setTimeStamp(localDateTime3);
    obdLog3.setObdLogTablePK(obdLogTablePK3);

    // HD190913 유저의 2024.04.01 로그2
    ObdLog obdLog4 = new ObdLog();
    ObdLogTablePK obdLogTablePK4 = new ObdLogTablePK();
    LocalDateTime localDateTime4 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    obdLog4.setDistance(1.0);
    obdLog4.setRpm(1.0);
    obdLog4.setCoolantTemp(1.0);
    obdLog4.setSpeed(1.0);
    obdLog4.setVin("A12345");
    obdLog4.setEngineLoad(1.0);
    obdLog4.setFuelLevel(1.0);
    obdLog4.setOilTemp(1.0);
    obdLog4.setRunTime(1.0);
    obdLog4.setRunTimeMIL(1.0);
    obdLog4.setThrottlePos(1.0);
    obdLogTablePK4.setDeviceId("HD190913");
    obdLogTablePK4.setTimeStamp(localDateTime4);
    obdLog4.setObdLogTablePK(obdLogTablePK4);

    // HD190913 유저의 2024.04.11 로그1
    ObdLog obdLog5 = new ObdLog();
    ObdLogTablePK obdLogTablePK5 = new ObdLogTablePK();
    LocalDateTime localDateTime5 = LocalDateTime.of(2024, 04, 11, 17, 15, 12);
    obdLog5.setDistance(1.0);
    obdLog5.setRpm(1.0);
    obdLog5.setCoolantTemp(1.0);
    obdLog5.setSpeed(1.0);
    obdLog5.setVin("A12345");
    obdLog5.setEngineLoad(1.0);
    obdLog5.setFuelLevel(1.0);
    obdLog5.setOilTemp(1.0);
    obdLog5.setRunTime(1.0);
    obdLog5.setRunTimeMIL(1.0);
    obdLog5.setThrottlePos(1.0);
    obdLogTablePK5.setDeviceId("HD190913");
    obdLogTablePK5.setTimeStamp(localDateTime5);
    obdLog5.setObdLogTablePK(obdLogTablePK5);

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
    List<ObdLog> dvId01and240401 = obdLogDataRepository.findObdLogByDeviceIdAndTimeStamp(deviceId1,
        start240401, end240401);

    // deviceId2의 240401 날짜의 모든 로그 출력 (expected: 2)
    List<ObdLog> dvId02and240401 = obdLogDataRepository.findObdLogByDeviceIdAndTimeStamp(deviceId1,
        start240401, end240401);

    // 240411 날짜의 모든 로그 출력 (expected: 1)
    List<ObdLog> dvId02and240411 = obdLogDataRepository.findObdLogByDeviceIdAndTimeStamp(deviceId2,
        start240411, end240411);

    assertThat(dvId01and240401.size()).isEqualTo(2);    // deviceId01 의 240401 날짜 로그가 2개가 아니라면, 오류
    assertThat(dvId02and240401.size()).isEqualTo(2);    // deviceId02 의 240401 날짜 로그가 2개가 아니라면, 오류
    assertThat(dvId02and240411.size()).isEqualTo(1);    // deviceId02 의 240411 날짜 로그가 1개가 아니라면, 오류
  }
}