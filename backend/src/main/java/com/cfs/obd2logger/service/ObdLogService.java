package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.ObdLogDTO;
import com.cfs.obd2logger.entity.DateRange;
import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.repository.ObdLogDataRepository;
import com.cfs.obd2logger.repository.UserRepository;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class ObdLogService {

  @Autowired
  ObdLogDataRepository obdLogDataRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * 라즈베리파이로부터 로그 받아서 저장
   */
  public boolean saveLog(ObdLogDTO obdLogDTO) {
    // 유효한 deviceId가 아닐 경우 저장하지 않음
    if (!isValidDeviceId(obdLogDTO.getDeviceId())) {
      return false;
    }

    ObdLog newObdLog = obdLogDTO.toEntity();
    obdLogDataRepository.save(newObdLog);
    return true;
  }

  /**
   * 라즈베리파이로부터 로그 리스트 받아서 저장
   */
  public boolean saveLog(List<ObdLogDTO> obdLogDTOList) {
    // 유효한 deviceId가 아닐 경우 저장하지 않음
    if (!isValidDeviceId(obdLogDTOList)) {
      return false;
    }

    List<ObdLog> newObdLogList = ListDTOToListEntity(obdLogDTOList);

    obdLogDataRepository.saveAll(newObdLogList);
    return true;
  }

  /**
   * 유저의 특정 날짜의 ObdLog 조회 (1일)
   */
  public List<ObdLogDTO> findObdLogOnDate(String deviceId, LocalDateTime date) {
    DateRange dateRange = new DateRange(date);
    LocalDateTime startDate = dateRange.getStartDate();
    LocalDateTime endDate = dateRange.getEndDate();

    try {
      List<ObdLog> obdLogList = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId, startDate,
          endDate);
      return ListEntityToListDTO(obdLogList);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 유저의 특정 날짜의 ObdLog 조회 (특정 시작일~특정 끝일)
   */
  public List<ObdLogDTO> findObdLogOnDate(String deviceId, LocalDateTime startDate,
      LocalDateTime endDate) {
    try {
      List<ObdLog> obdLogList = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId, startDate,
          endDate);
      return ListEntityToListDTO(obdLogList);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 유저의 로그 전체 삭제
   */
  public boolean deleteObdLog(String deviceId) {
    // 유효한 deviceId가 아닐 경우 삭제하지 않음
    if (!isValidDeviceId(deviceId)) {
      return false;
    }
    obdLogDataRepository.deleteAllByDeviceId(deviceId);
    return true;
  }

  /**
   * 특정 시간 range의 로그의 거리 계산 후 반환 (Killometer)
   */
  public double calDistance(String deviceId, LocalDateTime startDate, LocalDateTime endDate) {
    List<Double> lonList = obdLogDataRepository.findLonByDeviceAndTimeStamp(deviceId, startDate,
        endDate);
    List<Double> latList = obdLogDataRepository.findLatByDeviceAndTimeStamp(deviceId, startDate,
        endDate);

    // 로그가 0~1개일 경우, 0 반환
    int size = lonList.size();
    if (size < 2) {
      return 0;
    }

    double earthRadius = 6371000;                   // 미터 단위
    double dLat = 0.0;
    double dLon = 0.0;
    double lon1 = 0.0;
    double lat1 = 0.0;
    double lon2 = 0.0;
    double lat2 = 0.0;
    double a = 0.0;
    double c = 0.0;
    double dist = 0.0;

    for (int i = 0; i < size - 1; i++) {
      lon1 = lonList.get(i);
      lat1 = latList.get(i);
      lon2 = lonList.get(i + 1);
      lat2 = latList.get(i + 1);

      dLat = Math.toRadians(lat2 - lat1);
      dLon = Math.toRadians(lon2 - lon1);
      a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(
          Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
      c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      dist += earthRadius * c;
    }
    dist = dist / 1000.0;       // KM 단위 변환
    return dist;
  }

  // TODO : 파일 생성, 크기에 따른 성능 최적화

  /**
   * 특정 기간의 로그 DB를 엑셀 파일화
   */
  public ByteArrayResource createLogToExcel(String deviceId, String name, LocalDateTime startDate,
      LocalDateTime endDate) {
    try {
      // 사용자의 OBD 로그 불러오기
      List<ObdLog> obdLogList = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId, startDate,
          endDate);
      List<ObdLogDTO> obdLogDTOList = ListEntityToListDTO(obdLogList);

      // 파일 생성
      Workbook workbook = new XSSFWorkbook();

      // 시트 생성
      String sheetFile = name + "_CFS_LOG";
      Sheet sheet = workbook.createSheet(sheetFile);
      String[] headerStrings = {
          "DEVICE_ID", "TIME_STAMP",
          "VIN", "SPEED", "RPM",
          "ENGINE_LOAD", "FUEL_LEVEL",
          "BAROMETRIC_PRESSURE", "COOLANT_TEMP",
          "THROTTLE_POSE", "DISTANCE",
          "RUN_TIME", "LON", "LAT"};

      // 파일 작성 준비
      Row dataRow = null;
      Cell dataCell = null;

      // 헤더 작성
      int i = 0;
      int headerSize = headerStrings.length;
      dataRow = sheet.createRow(i);
      for (i = 0; i < headerSize; i++) {
        dataCell = dataRow.createCell(i);
        dataCell.setCellValue(headerStrings[i]);
      }

      // 데이터 행(i번째 행) 작성
      i = 1;
      for (ObdLogDTO dto : obdLogDTOList) {
        dataRow = sheet.createRow(i);

        dataCell = dataRow.createCell(0);
        dataCell.setCellValue(dto.getDeviceId());

        dataCell = dataRow.createCell(1);
        dataCell.setCellValue(dto.getTimeStamp());

        dataCell = dataRow.createCell(2);
        dataCell.setCellValue(dto.getVin());

        dataCell = dataRow.createCell(3);
        dataCell.setCellValue(dto.getSpeed());

        dataCell = dataRow.createCell(4);
        dataCell.setCellValue(dto.getRpm());

        dataCell = dataRow.createCell(5);
        dataCell.setCellValue(dto.getEngineLoad());

        dataCell = dataRow.createCell(6);
        dataCell.setCellValue(dto.getFuelLevel());

        dataCell = dataRow.createCell(7);
        dataCell.setCellValue(dto.getBarometricPressure());

        dataCell = dataRow.createCell(8);
        dataCell.setCellValue(dto.getCoolantTemp());

        dataCell = dataRow.createCell(9);
        dataCell.setCellValue(dto.getThrottlePos());

        dataCell = dataRow.createCell(10);
        dataCell.setCellValue(dto.getDistance());

        dataCell = dataRow.createCell(11);
        dataCell.setCellValue(dto.getRunTime());

        dataCell = dataRow.createCell(12);
        dataCell.setCellValue(dto.getLon());

        dataCell = dataRow.createCell(13);
        dataCell.setCellValue(dto.getLat());
      }

      // Response
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      workbook.write(byteArrayOutputStream);
      workbook.close();

      byte[] excelBytes = byteArrayOutputStream.toByteArray();
      return new ByteArrayResource(excelBytes);

    } catch (Exception e) {
      return null;
    }
  }


  /**
   * 유효한 deviceId 인지 검사하는 함수
   */
  private boolean isValidDeviceId(String deviceId) {
    try {
      UserEntity user = userRepository.findByDeviceId(deviceId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * obdLogDTO 리스트에 대하여 유효한 deviceId 인지 검사하는 함수
   */
  private boolean isValidDeviceId(List<ObdLogDTO> obdLogDTOList) {
    try {
      int listSize = obdLogDTOList.size();
      String deviceId;
      UserEntity user = null;
      for (ObdLogDTO obdDto : obdLogDTOList) {
        user = null;
        deviceId = obdDto.getDeviceId();
        user = userRepository.findByDeviceId(deviceId);
        if (user == null) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * obdLogEntity 리스트 --> obdLogDTO 리스트
   */
  private List<ObdLogDTO> ListEntityToListDTO(List<ObdLog> obdLogList) {
    return obdLogList.stream()
        .map(ObdLog::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * obdLogDTO 리스트 --> obdLogEntity 리스트
   */
  private List<ObdLog> ListDTOToListEntity(List<ObdLogDTO> obdLogDTOList) {
    return obdLogDTOList.stream()
        .map(ObdLogDTO::toEntity)
        .collect(Collectors.toList());
  }
}