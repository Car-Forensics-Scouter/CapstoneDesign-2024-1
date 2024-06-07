package com.cfs.obd2logger.repository;

import com.cfs.obd2logger.entity.User;
import com.cfs.obd2logger.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

  /**
   * deviceId, videoName이 일치하는 video를 조회하는 메소드
   */
  Video findByUserAndTitle(User user, String title);

  /**
   * user가 일치하는 모든 video를 조회하는 메소드
   */
  List<Video> findAllByUser(User user);

  /**
   * deviceId가 일치하는 모든 video를 삭제하는 메소드
   */
  @Modifying
  @Transactional
  int deleteAllByUser(User user);
}
