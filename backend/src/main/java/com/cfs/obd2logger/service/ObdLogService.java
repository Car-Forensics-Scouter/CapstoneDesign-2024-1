package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.ObdLogDTO;
import com.cfs.obd2logger.dto.ObdLogGpsDTO;
import com.cfs.obd2logger.dto.ObdLogSummaryAvgDTO;
import com.cfs.obd2logger.entity.DateRange;
import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.User;
import com.cfs.obd2logger.repository.ObdLogRepository;
import com.cfs.obd2logger.repository.UserRepository;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObdLogService {

  private final ObdLogRepository obdLogDataRepository;
  private final UserRepository userRepository;

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
    if (!isValidDeviceId(obdLogDTOList)) {
      return false;
    }

    List<ObdLog> obdLogList = ListDTOToListEntity(obdLogDTOList);
    obdLogDataRepository.saveAll(obdLogList);
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
   * 유저의 로그 전체 삭제 // TODO : CASCADE로 자동으로 삭제되게끔
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
   * 요약 정보 (리스트)
   */
  public List<ObdLogGpsDTO> getSummaryList(String deviceId, LocalDateTime startDate,
      LocalDateTime endDate) {
    List<ObdLogGpsDTO> summaryListDTO = obdLogDataRepository.findObdLogGPSByDeviceIdAndTimeStamp(
        deviceId, startDate, endDate);
    return summaryListDTO;
  }

  /**
   * 요약 정보 (평균)
   */
  public ObdLogSummaryAvgDTO getSummaryAvg(String deviceId, LocalDateTime startDate,
      LocalDateTime endDate) {
    List<ObdLogDTO> obdLogDTOList = findObdLogOnDate(deviceId, startDate, endDate);
    int len = obdLogDTOList.size();
    double runtime = obdLogDTOList.get(0).getRunTime();
    double speed = calAvg(obdLogDTOList, ObdLogDTO::getSpeed, len);
    double rpm = calAvg(obdLogDTOList, ObdLogDTO::getRpm, len);
    double engineLoad = calAvg(obdLogDTOList, ObdLogDTO::getEngineLoad, len);
    double fuelLevel = calAvg(obdLogDTOList, ObdLogDTO::getFuelLevel, len);
    double throttlePos = calAvg(obdLogDTOList, ObdLogDTO::getThrottlePos, len);
    double barometicPressure = calAvg(obdLogDTOList, ObdLogDTO::getBarometricPressure, len);
    double coolantTemp = calAvg(obdLogDTOList, ObdLogDTO::getCoolantTemp, len);
    double distance = calDistance(deviceId, startDate, endDate);
    String vin = obdLogDTOList.get(0).getVin();
    return ObdLogSummaryAvgDTO.builder()
        .runtime(runtime)
        .speed(speed)
        .rpm(rpm)
        .engineLoad(engineLoad)
        .fuelLevel(fuelLevel)
        .throttlePos(throttlePos)
        .barometricPressure(barometicPressure)
        .coolantTemp(coolantTemp)
        .distance(distance)
        .vin(vin).build();
  }

  /**
   * 공통 평균값 계산
   */
  public double calAvg(List<ObdLogDTO> obdLogDTOList, ToDoubleFunction<ObdLogDTO> mapper,
      int len) {
    if (len == 0) {
      return 0.0;
    }
    double total = 0.0;
    for (ObdLogDTO obdLogDTO : obdLogDTOList) {
      total += mapper.applyAsDouble(obdLogDTO);
    }
    return total / len;
  }

  /**
   * 특정 시간의 총 거리 계산 후 반환 (Killometer)
   */
  public double calDistance(String deviceId, LocalDateTime startDate, LocalDateTime endDate) {
    List<ObdLogGpsDTO> GPSList = obdLogDataRepository.findObdLogGPSByDeviceIdAndTimeStamp(deviceId,
        startDate, endDate);
    // 로그가 0~1개일 경우, 0 반환
    int size = GPSList.size();
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
      lon1 = GPSList.get(i).getLon();
      lat1 = GPSList.get(i).getLat();
      lon2 = GPSList.get(i + 1).getLon();
      lat2 = GPSList.get(i + 1).getLat();

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


  /**
   * 특정 기간의 로그 DB를 엑셀 파일화
   */
  public ByteArrayResource createLogToExcel(String deviceId, LocalDateTime startDate,
      LocalDateTime endDate) {
    try {
      // 사용자의 OBD 로그 불러오기
      List<ObdLog> obdLogList = obdLogDataRepository.findByDeviceIdAndTimeStamp(deviceId, startDate,
          endDate);
      List<ObdLogDTO> obdLogDTOList = ListEntityToListDTO(obdLogList);

      // 파일 생성
      Workbook workbook = new XSSFWorkbook();

      // 시트 생성
      String sheetFile = "CFS_LOG";
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

      // 헤더 스타일 생성
      CellStyle headerStyle = workbook.createCellStyle();
      Font font = workbook.createFont();
      font.setBold(true);                       // 볼드체
      font.setFontHeightInPoints((short) 13);   // 폰트 크기
      headerStyle.setFont(font);
      headerStyle.setBorderBottom(BorderStyle.THICK);
      headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
      headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      // 헤더 작성
      int i = 0;
      int headerSize = headerStrings.length;
      sheet.createFreezePane(0, 1);
      dataRow = sheet.createRow(i);
      for (i = 0; i < headerSize; i++) {
        dataCell = dataRow.createCell(i);
        dataCell.setCellValue(headerStrings[i]);
        dataCell.setCellStyle(headerStyle);                             // 헤더 셀 스타일 적용
        sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 512);    // 셀 너비 확장
      }
      // 데이터 행(i번째 행) 작성
      i = 1;
      for (ObdLogDTO dto : obdLogDTOList) {
        dataRow = sheet.createRow(i);
        dataRow.createCell(0).setCellValue(dto.getDeviceId());
        dataRow.createCell(1).setCellValue(
            dto.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dataRow.createCell(2).setCellValue(dto.getVin());
        dataRow.createCell(3).setCellValue(dto.getSpeed());
        dataRow.createCell(4).setCellValue(dto.getRpm());
        dataRow.createCell(5).setCellValue(dto.getEngineLoad());
        dataRow.createCell(6).setCellValue(dto.getFuelLevel());
        dataRow.createCell(7).setCellValue(dto.getBarometricPressure());
        dataRow.createCell(8).setCellValue(dto.getCoolantTemp());
        dataRow.createCell(9).setCellValue(dto.getThrottlePos());
        dataRow.createCell(10).setCellValue(dto.getDistance());
        dataRow.createCell(11).setCellValue(dto.getRunTime());
        dataRow.createCell(12).setCellValue(dto.getLon());
        dataRow.createCell(13).setCellValue(dto.getLat());
        i++;
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
      User user = userRepository.findByDeviceId(deviceId);
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
      User user = null;
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