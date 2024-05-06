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
   * 특정 deviceId의 startDate에서 endDate 사이의 Lon을 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog.lon FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :deviceId AND obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<Double> findLonByDeviceAndTimeStamp(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  /**
   * 특정 deviceId의 startDate에서 endDate 사이의 Lat을 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog.lat FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :deviceId AND obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<Double> findLatByDeviceAndTimeStamp(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);


  /**
   * deviceId가 일치하는 모든 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :startDate")
  List<ObdLog> findObdLogByDeviceId(@Param("deviceId") String deviceId);

  /**
   * 특정 deviceId의 startDate에서 endDate 사이의 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.deviceId = :deviceId AND obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findObdLogByDeviceIdAndTimeStamp(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}