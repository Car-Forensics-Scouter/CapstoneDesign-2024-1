package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.service.ObdLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/obd")
@RequiredArgsConstructor
public class ObdLogController {

  @Autowired
  private final ObdLogService obdLogService;

  // TODO : HTTP API POST 라즈베리 파이로부터 로그 json 데이터 받기

  /**
   * 라즈베리 파이로부터 json 데이터 저장
   */
  @PostMapping("")
  public ResponseEntity<?> saveObdLog(@RequestParam String deviceId) {
    return ResponseEntity.ok().body("true");
  }

  /**
   * 로그 날짜 별로 조회
   */
  @GetMapping("")
  public ResponseEntity<?> getObdLogOnDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDateTime date) {
    try {
      List<ObdLog> obdLogsOnDate = obdLogService.findObdLogOnDate(deviceId, date);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());    // TODO : Not-Found로 바꿀 것
    }
  }
}
