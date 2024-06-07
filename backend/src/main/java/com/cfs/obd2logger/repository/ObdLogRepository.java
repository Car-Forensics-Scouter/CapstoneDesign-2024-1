package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.dto.ObdLogGpsDTO;
import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ObdLogRepository extends JpaRepository<ObdLog, ObdLogTablePK> {

  /**
   * deviceId가 일치하는 모든 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT o FROM ObdLog o WHERE o.obdLogTablePK.deviceId = :deviceId")
  List<ObdLog> findAllByDeviceId(@Param("deviceId") String deviceId);

  /**
   * deviceId가 일치하는 모든 obdLog를 삭제하고 삭제된 컬럼 갯수를 반환하는 쿼리 메소드
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM ObdLog o WHERE o.obdLogTablePK.deviceId = :deviceId")
  int deleteAllByDeviceId(@Param("deviceId") String deviceId);

  /**
   * 특정 device의 startDate에서 endDate 사이에서 요약 정보(List) 조회하는 메소드
   */
  @Query("SELECT new com.cfs.obd2logger.dto.ObdLogSummaryAvgDTO(o.speed, o.rpm, o.engineLoad, o.fuelLevel, o.throttlePos, o.barometricPressure, o.coolantTemp, 0.0, o.vin) FROM ObdLog o WHERE o.obdLogTablePK.deviceId = :deviceId AND o.obdLogTablePK.timeStamp >= :startDate AND o.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLogGpsDTO> findObdLogSummaryAvgByDeviceIdAndTimeStamp(
      @Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  /**
   * 특정 device의 startDate에서 endDate 사이에서 GPS 정보(List) 조회하는 메소드
   */
  @Query("SELECT new com.cfs.obd2logger.dto.ObdLogGpsDTO(o.lon, o.lat) FROM ObdLog o WHERE o.obdLogTablePK.deviceId = :deviceId AND o.obdLogTablePK.timeStamp >= :startDate AND o.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLogGpsDTO> findObdLogGPSByDeviceIdAndTimeStamp(
      @Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);


  /**
   * 특정 deviceId의 startDate에서 endDate 사이의 obdLog를 조회하는 쿼리 메소드
   */
  @Query("SELECT o FROM ObdLog o WHERE o.obdLogTablePK.deviceId = :deviceId AND o.obdLogTablePK.timeStamp >= :startDate AND o.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findByDeviceIdAndTimeStamp(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}