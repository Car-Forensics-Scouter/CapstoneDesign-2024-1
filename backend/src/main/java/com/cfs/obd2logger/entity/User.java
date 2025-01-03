package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @Column(name = "USER_ID", length = 20, nullable = false)
  private String id;

  @Column(name = "DEVICE_ID", length = 20, unique = true, nullable = false)
  private String deviceId;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Column(name = "USER_NAME", length = 20, nullable = false)
  private String name;

  @Column(name = "EMAIL", length = 100, nullable = false)
  private String email;

  @Column(name = "CAR_NAME", length = 20, nullable = false)
  private String carName;

  @Column(name = "STATUS", length = 20, nullable = false)
  private String status;
}