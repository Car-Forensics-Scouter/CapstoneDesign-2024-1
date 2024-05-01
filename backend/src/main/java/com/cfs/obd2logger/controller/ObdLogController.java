package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.service.ObdLogService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ObdLogController {

  @Autowired
  ObdLogService obdLogService;

  // TODO : HTTP API POST 라즈베리 파이로부터 로그 json 데이터 받기

  /**
   * 로그 날짜 별로 조회
   */
  @GetMapping("/")
  public ResponseEntity<?> getLogByIdAndDate(@RequestParam String deviceId,
      @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd")
      LocalDateTime date) {
    try {
      List<ObdLog> obdLogsOnDate = obdLogService.findObdLogByDeviceAndDay(deviceId, date);
      return ResponseEntity.ok().body(obdLogsOnDate);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
