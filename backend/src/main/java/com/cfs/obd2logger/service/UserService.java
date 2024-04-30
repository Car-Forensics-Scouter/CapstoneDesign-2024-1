package com.cfs.obd2logger.service;

import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // 회원 가입
    public UserEntity signup(UserEntity userEntity) {
        if (userEntity == null) {
            throw new RuntimeException("아이디를 입력하세요");
        }
        // 회원 상태 변경
        userEntity.setStatus("activated");
        return userRepository.save(userEntity);
    }

    // 로그인
    public UserEntity login(String id, String password) {
        // UserEntity user = UserRepository.findByid(id).get();
        // 비밀번호와 비밀번호 토근 비교
        return null;
    }

    // 아이디 중복검사
    public boolean checkId(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) return true;
        return false;
    }

    // 사용자 정보
    public UserEntity getUserInfo(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        return null;
    }

    // 회원정보 수정 (고려중) -> 비밀번호, 차종, 제품 일련번호

    // 아이디 비밀번호 찾기

    // 회원 탈퇴
    public String deleteUser(String id, String email) {
        UserEntity user = userRepository.findByIdAndPassword(id, email);
        if (user == null) {
            throw new RuntimeException("입력이 잘못되었습니다.");
        }
        user.setStatus("deactivated");
        userRepository.save(user);
        return "탈퇴 완료.";
    }
}
