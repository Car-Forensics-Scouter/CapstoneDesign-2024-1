package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByNameAndEmail(String name, String email);
    User findByIdAndNameAndEmail(String id, String name, String email);
    User findByDeviceId(String deviceId);
}