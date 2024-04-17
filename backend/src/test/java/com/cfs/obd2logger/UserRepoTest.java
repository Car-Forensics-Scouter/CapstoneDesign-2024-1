package com.cfs.obd2logger;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {

    @Autowired UserRepo userRepo;
    @Test
    @Transactional
    @Rollback(false)                                // 테스트가 완료된 후 데이터베이스 상태 유지
    public void testMember() {
        User user = new User();                     // 객체 생성
        user.setUsername("Hello");                  // 객체 이름 설정

        Long savedId = userRepo.save(user);         // User 객체를 데이터베이스에 저장, 객체 ID 반환
        User findUser = userRepo.find(savedId);     // 저장된 User의 객체 ID를 사용해 데이터베이스 조회

        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());                // findMember 객체 ID와 user 객체 ID 비교
        Assertions.assertThat(findUser.getUsername()).isEqualTo(user.getUsername());    // findMember 객체 사용자 이름과 user 객체 사용자 이름 비교
        Assertions.assertThat(findUser).isEqualTo(user);                                // findMember 객체와 user 객체 전체 비교
    }
}