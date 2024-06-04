package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByNameAndEmail(String name, String email);
    UserEntity findByIdAndNameAndEmail(String id, String name, String email);
    UserEntity findByDeviceId(String deviceId);
}