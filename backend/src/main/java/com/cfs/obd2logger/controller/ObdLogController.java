package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.ObdLogDTO;
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

  // TODO : 컨트롤러 테스팅 필요

  /**
   * 라즈베리 파이로부터 json 데이터 저장
   */
  @PostMapping("/save")
  public ResponseEntity<?> saveObdLog(@RequestBody List<ObdLogDTO> obdLogDTOList) {
    boolean isInvalid = obdLogService.saveLog(obdLogDTOList);

    // 유효한 DeviceId
    if (isInvalid) {
      return ResponseEntity.ok().body("Invalid - Log Saved");
    } else {
      return ResponseEntity.badRequest().body("Invalid DeviceId");
    }
  }

  /**
   * 특정 날짜의 로그 조회 (1일)
   */
  @GetMapping("/date")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
    try {
      List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, date);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 날짜의 로그 조회 (특정 시작일~특정 끝일)
   */
  @GetMapping("/date-range")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
    try {
      List<ObdLogDTO> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, startDate, endDate);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 특정 날짜의 거리 계산
   */
  @GetMapping("/distance")
  public ResponseEntity<?> getDistanceOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
    try {
      double distance = obdLogService.calDistance(deviceId, startDate, endDate);
      return ResponseEntity.ok().body(distance);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // not-found 시 body에 에러 메세지 표기 불가
    }
  }

  /**
   * 로그 파일 다운로드
   */
  @GetMapping("/download")
  public ResponseEntity<?> downloadObdLog(@RequestParam String deviceId,
      @RequestParam String name) {
    try {
      ByteArrayResource excelFile = obdLogService.createLogToExcel(deviceId, name);
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
