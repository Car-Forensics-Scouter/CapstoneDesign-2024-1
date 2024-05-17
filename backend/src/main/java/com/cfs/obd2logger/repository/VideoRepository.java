package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, UserEntity> {

  /*
   * user가 일치하는 모든 video를 조회하는 메소드
   */
  List<Video> findVideoByUser(UserEntity user);
}
