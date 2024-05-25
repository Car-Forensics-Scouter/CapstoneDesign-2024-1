package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.ObdLogDTO;
import com.cfs.obd2logger.dto.ObdLogSummaryAvgDTO;
import com.cfs.obd2logger.dto.ObdLogGpsDTO;
import com.cfs.obd2logger.service.ObdLogService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/obdlog")
@RequiredArgsConstructor
public class ObdLogController {

  @Autowired
  private final ObdLogService obdLogService;

  /**
   * 라즈베리 파이로부터 json 데이터 저장 (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
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
   * 특정 날짜의 로그 조회 (1일)
   * (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
   */
  @GetMapping("/date")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime date) {
    try {
      List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, date);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 날짜의 로그 조회 (특정 시작일~특정 끝일)
   * (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
   */
  @GetMapping("/date-range")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
    try {
      List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, startDate, endDate);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 날짜의 요약 정보 계산(리스트)
   * (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
   */
  @GetMapping("/summary-list")
  public ResponseEntity<?> getListSummaryOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
    try {
      List<ObdLogGpsDTO> logSummaryDTO = obdLogService.getSummaryList(deviceId, startDate,
          endDate);
      return ResponseEntity.ok()
          .body(logSummaryDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 날짜의 요약 정보 계산(평균)
   * (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
   */
  @GetMapping("/summary-avg")
  public ResponseEntity<?> getAvgSummaryOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
    try {
      ObdLogSummaryAvgDTO logSummaryDTO = obdLogService.getSummaryAvg(deviceId, startDate, endDate);
      return ResponseEntity.ok()
          .body(logSummaryDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 기간의 로그 파일 다운로드
   * (요청 날짜 포맷: yyyy-mm-hhThh:mm:ss)
   */
  @GetMapping("/download")
  public ResponseEntity<?> downloadObdLog(@RequestParam String deviceId,
      @RequestParam String name,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
    try {
      ByteArrayResource excelFile = obdLogService.createLogToExcel(deviceId, name, startDate,
          endDate);
      String filename = URLEncoder.encode(name + "_CFS_LOG.xlsx", StandardCharsets.UTF_8);

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
      headers.add(HttpHeaders.CONTENT_TYPE,
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

      return ResponseEntity.ok()
          .headers(headers)
          .body(excelFile);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }
}
