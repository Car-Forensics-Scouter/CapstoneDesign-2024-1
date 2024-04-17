package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

  @Column(name = "DEVICE_ID", nullable = false)
  private Long deviceId;

  @Column(name = "TIME_STAMP", nullable = false)
  private LocalDateTime timeStamp;

  public ObdLogTablePK(Long deviceId, LocalDateTime timeStamp) {
    this.deviceId = deviceId;
    this.timeStamp = timeStamp;
  }
}
