package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.User;
import com.cfs.obd2logger.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByIdAndPassword(String id, String password);
    UserEntity findByNameAndEmail(String name, String email);
    UserEntity findByDeviceId(String deviceId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserEntity SET status = 'deactivated' WHERE id = :userId")
    void deactivateById(@Param("userId") String userId);
}