package com.cfs.obd2logger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ObdLogGpsDTO {
  private double lon;               // GPS 경도
  private double lat;               // GPS 위도
}
