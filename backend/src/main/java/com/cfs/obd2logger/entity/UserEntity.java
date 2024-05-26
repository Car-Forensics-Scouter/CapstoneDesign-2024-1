package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class UserEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID", length = 20, nullable = false)
  private String id;

  @Column(name = "PASSWORD", length = 20, nullable = false)
  private String password;

  @Column(name = "USER_NAME", length = 20, nullable = false)
  private String name;

  @Column(name = "EMAIL", length = 20, nullable = false)
  private String email;

  @Column(name = "CAR_NAME", length = 20, nullable = false)
  private String carName;

  @Column(name = "DEVICE_ID", length = 8, nullable = false)
  private String deviceId;

  @Column(name = "STATUS", length = 20, nullable = false)
  private String status;
}