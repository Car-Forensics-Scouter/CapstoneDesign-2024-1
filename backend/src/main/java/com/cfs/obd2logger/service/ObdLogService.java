package com.cfs.obd2logger.service;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.repository.ObdLogDataRepository;
import com.cfs.obd2logger.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObdLogService {

  // TODO : 추후 스프링빈 최적화
  @Autowired
  ObdLogDataRepository obdLogDataRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * 라즈베리파이로부터 로그 받아서 저장
   */
  public void saveLog(ObdLog obdLog) {
    // 유효한 deviceId가 아닐 경우 저장하지 않음
    if (!isValidDeviceId(obdLog.getObdLogTablePK().getDeviceId())) {
      return;
    }
    obdLogDataRepository.save(obdLog);
  }

  /**
   * 유저의 특정 날짜의 ObdLog 조회
   */
  public List<ObdLog> findObdLogOnDate(String deviceId, LocalDateTime date) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();

    LocalDateTime startDate = LocalDateTime.of(year, month, day, 00, 00, 00);
    LocalDateTime endDate = LocalDateTime.of(year, month, day, 23, 59, 59);

    try {
      return obdLogDataRepository.findObdLogByDeviceIdAndTimeStamp(deviceId, startDate, endDate);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 유효한 deviceId 인지 검사하는 함수
   */
  private boolean isValidDeviceId(String deviceId) {
    try {
      // UserEntity user = userRepository.findByDeviceId(deviceId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
