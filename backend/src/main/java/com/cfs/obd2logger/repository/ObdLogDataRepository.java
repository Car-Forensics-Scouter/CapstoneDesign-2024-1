package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.ObdLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObdLogDataRepository extends JpaRepository<ObdLog, String> {

}