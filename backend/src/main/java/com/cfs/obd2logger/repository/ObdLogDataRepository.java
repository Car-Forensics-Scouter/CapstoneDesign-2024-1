package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObdLogDataRepository extends JpaRepository<ObdLog, ObdLogTablePK> {

  /**
   * deviceId가 일치하는 모든 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :startDate")
  List<ObdLog> findObdLogByDeviceId(@Param("deviceId") String deviceId);


  /**
   * startDate에서 endDate 사이의 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findObdLogByDay(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  /**
   * 특정 deviceId의 startDate에서 endDate 사이의 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :deviceId AND obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findObdLogByDeviceidAndDay(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}