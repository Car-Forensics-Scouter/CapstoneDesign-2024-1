package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.ObdLog;
import com.cfs.obd2logger.entity.ObdLogTablePK;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ObdLogDataRepositoryTest {

  @PersistenceContext
  EntityManager em;

  @Test
  public void testObdLogData() {
    ObdLog obdLog1 = new ObdLog();
    ObdLogTablePK obdLogTablePK1 = new ObdLogTablePK();
    LocalDateTime localDateTime1 = LocalDateTime.now();
    obdLog1.setDistance(1.0);
    obdLog1.setRpm(1.0);
    obdLog1.setCoolantTemp(1.0);
    obdLog1.setSpeed(1.0);
    obdLog1.setVin("ABCDE");
    obdLog1.setEngineLoad(1.0);
    obdLog1.setFuelLevel(1.0);
    obdLog1.setOilTemp(1.0);
    obdLog1.setRunTime(1.0);
    obdLog1.setRunTimeMIL(1.0);
    obdLog1.setThrottlePos(1.0);
    obdLogTablePK1.setDeviceId("VF190913");
    obdLogTablePK1.setTimeStamp(localDateTime1);
    obdLog1.setObdLogTablePK(obdLogTablePK1);

    System.out.println(obdLog1);
  }
}