package com.cfs.obd2logger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USER")
public class UserEntity {

  @Id
  @GeneratedValue
  @Column(name = "USER_ID", length = 20, nullable = false)
  private String id;

  @Column(name = "PASSWORD", length = 20, nullable = false)
  private String password;

  @Column(name = "USER_NAME", length = 20, nullable = false)
  private String name;

  @Column(name = "EMAIL", length = 20, nullable = false)
  private String email;

  @Column(name = "CAR_NAME", length = 20, nullable = false)
  private String car_name;

  @Column(name = "DEVICE_ID", length = 8, nullable = false)
  private String device_id;

  @Column(name = "STATUS", length = 20, nullable = false)
  private String status = "activated";
}