package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
public class ObdLogTablePK implements Serializable {

  @Column(name = "DEVICE_ID", length = 8, nullable = false, unique = true)
  private String deviceId;

  @Column(name = "TIME_STAMP", nullable = false)
  private LocalDateTime timeStamp;

  public ObdLogTablePK(String deviceId, LocalDateTime timeStamp) {
    this.deviceId = deviceId;
    this.timeStamp = timeStamp;
  }
}
