package com.cfs.obd2logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cfs.obd2logger.dto.VideoDTO;
import com.cfs.obd2logger.entity.DateRange;
import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import com.cfs.obd2logger.entity.User;
import com.cfs.obd2logger.service.ObdLogService;
import com.cfs.obd2logger.service.UserService;
import com.cfs.obd2logger.service.VideoService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ObdLogRepositoryTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObdLogService obdLogService;

  @Autowired
  ObdLogRepository obdLogRepository;

  @Autowired
  VideoService videoService;

  @Autowired
  VideoRepository videoRepository;

  // 테스트용 deviceId, 추후 테스팅 자동화 할 것
  String deviceId1 = "VF190913";
  String deviceId2 = "HD190913";

  @Test
  public void testVideoFind() {
    User user = User.builder()
        .deviceId(deviceId1)
        .id(deviceId1)
        .password(deviceId1)
        .carName("AVANTE")
        .email("adsf@gmail.com")
        .name("VICTOR")
        .status("activated").build();
    userService.signup(user);
    System.out.println("SIGNED USER: " + userRepository.findByDeviceId(deviceId1));
    videoService.saveVideo(deviceId1, "thumbnail", "TESTING_VIDEO", 25,
        LocalDateTime.of(2024, 05, 05, 10, 10, 10));
    System.out.println("FIND VIDEO:" + videoService.findVideo(deviceId1, "TESTING_VIDEO"));
    videoService.saveVideo(deviceId1, "thumbnail", "TESTING22_VIDEO", 25,
        LocalDateTime.of(2024, 05, 05, 10, 10, 10));
    System.out.println("FIND VIDEO:" + videoService.findVideo(deviceId1, "TESTING22_VIDEO"));
    videoService.saveVideo(deviceId1, "thumbnail", "TESTING33_VIDEO", 25,
        LocalDateTime.of(2024, 05, 05, 10, 10, 10));
    System.out.println("FIND VIDEO:" + videoService.findVideo(deviceId1, "TESTING33_VIDEO"));
    videoService.saveVideo(deviceId1, "thumbnail", "TESTING44_VIDEO", 25,
        LocalDateTime.of(2024, 05, 05, 10, 10, 10));
    System.out.println("FIND VIDEO:" + videoService.findVideo(deviceId1, "TESTING44_VIDEO"));
    List<VideoDTO> videoList = videoService.findAllVideo(deviceId1);
    System.out.println("VIDEO LIST!!" + videoList);
  }

  @Test
  public void testDownload() {
    testDataGenerator();
    LocalDateTime start240411 = LocalDateTime.of(2024, 04, 11, 00, 00, 00);
    LocalDateTime end240411 = LocalDateTime.of(2024, 04, 11, 23, 59, 59);

    // 로그 -> 엑셀 파일화 실행
    ByteArrayResource excelContents = obdLogService.createLogToExcel(deviceId2, "henry",
        start240411, end240411);

    byte[] sampleBytes = {0x50, 0x4B, 0x03, 0x04}; // 엑셀 파일 시작
    System.out.println("CREATE EXCEL!!");

    byte[] bytes = excelContents.getByteArray();

    System.out.println("EXCEL LENGTH: " + bytes.length);

    // HEX 출력
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      sb.append(String.format("%02X ", bytes[i]));
      if ((i + 1) % 16 == 0) {
        sb.append("\n");
      }
    }

    System.out.println("HEX DUMP:\n" + sb);
  }

  @Test
  public void testRemoveAll() {
    testDataGenerator();

    int deleted = obdLogRepository.deleteAllByDeviceId(deviceId2);
    assertThat(deleted).isEqualTo(3);       // 3개 삭제된 게 아닐 시 오류
  }

  @Test
  public void testCalDistance() {
    testDataGenerator();

    double dist = 0;
    LocalDateTime localDateTime = LocalDateTime.of(2024, 04, 01, 01, 01, 01);
    DateRange dateRange = new DateRange(localDateTime);
    dist = obdLogService.calDistance(deviceId1, dateRange.getStartDate(), dateRange.getEndDate());

    System.out.println("DISTANCE!! - " + dist);
  }

  /**
   * 테스트용 데이터 생성 deviceId1 - 240401 (2) / deviceId2 - 240401 (2), 240411 (1)
   */
  public void testDataGenerator() {
    // VF190913 유저의 2024.04.01 로그1
    LocalDateTime localDateTime1 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    ObdLogTablePK obdLogTablePK1 = ObdLogTablePK.builder()
        .deviceId(deviceId1)
        .timeStamp(localDateTime1).build();
    ObdLog obdLog1 = ObdLog.builder()
        .obdLogTablePK(obdLogTablePK1)
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("A12345")
        .engineLoad(1.0)
        .barometricPressure(1.0)
        .fuelLevel(1.0)
        .runTime(1.0)
        .throttlePos(1.0)
        .lon(127.0)
        .lat(37.0).build();

    // VF190913 유저의 2024.04.01 로그2
    LocalDateTime localDateTime2 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    ObdLogTablePK obdLogTablePK2 = ObdLogTablePK.builder()
        .deviceId(deviceId1)
        .timeStamp(localDateTime2).build();

    ObdLog obdLog2 = ObdLog.builder()
        .obdLogTablePK(obdLogTablePK2)
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("A12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .barometricPressure(1.0)
        .runTime(1.0)
        .throttlePos(1.0)
        .lon(128.0)
        .lat(38.0).build();

    // HD190913 유저의 2024.04.01 로그1
    LocalDateTime localDateTime3 = LocalDateTime.of(2024, 04, 01, 12, 12, 12);
    ObdLogTablePK obdLogTablePK3 = ObdLogTablePK.builder()
        .deviceId(deviceId2)
        .timeStamp(localDateTime3).build();

    ObdLog obdLog3 = ObdLog.builder()
        .obdLogTablePK(obdLogTablePK3)
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .barometricPressure(1.0)
        .runTime(1.0)
        .throttlePos(1.0)
        .lon(127.0)
        .lat(37.0).build();

    // HD190913 유저의 2024.04.01 로그2
    LocalDateTime localDateTime4 = LocalDateTime.of(2024, 04, 01, 12, 15, 12);
    ObdLogTablePK obdLogTablePK4 = ObdLogTablePK.builder()
        .deviceId(deviceId2)
        .timeStamp(localDateTime4).build();

    ObdLog obdLog4 = ObdLog.builder()
        .obdLogTablePK(obdLogTablePK4)
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .barometricPressure(1.0)
        .runTime(1.0)
        .throttlePos(1.0)
        .lon(127.0)
        .lat(37.0).build();

    // HD190913 유저의 2024.04.11 로그1
    LocalDateTime localDateTime5 = LocalDateTime.of(2024, 04, 11, 17, 15, 12);
    ObdLogTablePK obdLogTablePK5 = ObdLogTablePK.builder()
        .deviceId(deviceId2)
        .timeStamp(localDateTime5).build();

    ObdLog obdLog5 = ObdLog.builder()
        .obdLogTablePK(obdLogTablePK5)
        .distance(1.0)
        .rpm(1.0)
        .coolantTemp(1.0)
        .speed(1.0)
        .vin("B12345")
        .engineLoad(1.0)
        .fuelLevel(1.0)
        .barometricPressure(1.0)
        .runTime(1.0)
        .throttlePos(1.0)
        .lon(127.0)
        .lat(37.0).build();

    obdLogRepository.save(obdLog1);
    obdLogRepository.save(obdLog2);
    obdLogRepository.save(obdLog3);
    obdLogRepository.save(obdLog4);
    obdLogRepository.save(obdLog5);

    LocalDateTime start240401 = LocalDateTime.of(2024, 04, 01, 00, 00, 00);
    LocalDateTime end240401 = LocalDateTime.of(2024, 04, 01, 23, 59, 59);

    LocalDateTime start240411 = LocalDateTime.of(2024, 04, 11, 00, 00, 00);
    LocalDateTime end240411 = LocalDateTime.of(2024, 04, 11, 23, 59, 59);
  }
}