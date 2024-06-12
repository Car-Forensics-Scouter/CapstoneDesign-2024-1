package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.ObdLogDTO;
import com.cfs.obd2logger.dto.ObdLogGpsDTO;
import com.cfs.obd2logger.dto.ObdLogSummaryAvgDTO;
import com.cfs.obd2logger.service.ObdLogService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/obdlog")
public class ObdLogController {

  private final ObdLogService obdLogService;

  /**
   * 라즈베리 파이로부터 json 데이터 저장 (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34:56
   */
  @PostMapping("/save")
  public ResponseEntity<?> saveObdLog(@RequestBody List<ObdLogDTO> obdLogDTOList) {
    boolean isInvalid = obdLogService.saveLog(obdLogDTOList);

    // 유효한 DeviceId
    if (isInvalid) {
      return ResponseEntity.ok().body("Valid - Log Saved");
    } else {
      return ResponseEntity.badRequest().body("Invalid DeviceId");
    }
  }

  /**
   * 특정 날짜의 로그 조회 (1일) (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34
   */
  @GetMapping("/date")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date) {
    List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, date);
    return ResponseEntity.ok().body(obdLogsOnDate);
  }

  /**
   * 특정 날짜의 로그 조회 (특정 시작일~특정 끝일) (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34
   */
  @GetMapping("/date-range")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
    List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, startDate, endDate);
    return ResponseEntity.ok().body(obdLogsOnDate);
  }

  /**
   * 특정 날짜의 요약 정보 계산(리스트) (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34
   */
  @GetMapping("/summary-list")
  public ResponseEntity<?> getListSummaryOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
    List<ObdLogGpsDTO> logSummaryDTO = obdLogService.getSummaryList(deviceId, startDate, endDate);
    return ResponseEntity.ok().body(logSummaryDTO);
  }

  /**
   * 특정 날짜의 요약 정보 계산(평균) (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34
   */
  @GetMapping("/summary-avg")
  public ResponseEntity<?> getAvgSummaryOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
    ObdLogSummaryAvgDTO logSummaryDTO = obdLogService.getSummaryAvg(deviceId, startDate, endDate);
    return ResponseEntity.ok().body(logSummaryDTO);
  }

  /**
   * 특정 기간의 로그 파일 다운로드 (요청 날짜 포맷: yyyy-MM-dd'T'HH:mm:ss) ex) 2020-11-11T12:34:56
   */
  @GetMapping("/download")
  public ResponseEntity<?> downloadObdLog(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
    ByteArrayResource excelFile = obdLogService.createLogToExcel(deviceId, startDate, endDate);
    String filename = URLEncoder.encode("CFS_LOG.xlsx", StandardCharsets.UTF_8);

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    return ResponseEntity.ok()
        .headers(headers)
        .body(excelFile);
  }
}