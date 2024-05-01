package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObdLogDataRepository extends JpaRepository<ObdLog, ObdLogTablePK> {

  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findObdLogByDay(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  @Query("SELECT obdLog FROM ObdLog obdLog WHERE obdLog.obdLogTablePK.device_id = :deviceId AND obdLog.obdLogTablePK.timeStamp >= :startDate AND obdLog.obdLogTablePK.timeStamp <= :endDate")
  List<ObdLog> findObdLogByDeviceidAndDay(@Param("deviceId") String deviceId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}