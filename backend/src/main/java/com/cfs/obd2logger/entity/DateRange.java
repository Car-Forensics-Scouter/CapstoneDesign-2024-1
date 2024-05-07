package com.cfs.obd2logger.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

// 하루 날짜의 시작-끝 범위
@Getter
public class DateRange {

  private final LocalDateTime startDate;
  private final LocalDateTime endDate;

  public DateRange(LocalDateTime date) {
    this.startDate = date.toLocalDate().atTime(LocalTime.MIN);
    this.endDate = date.toLocalDate().atTime(LocalTime.MAX);
  }
}