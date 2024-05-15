package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.repository.UserRepository;
import jakarta.persistence.Entity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    // 회원 가입
    public UserEntity signup(UserEntity userEntity) {
        if (userEntity == null) {
            throw new RuntimeException("정보를 모두 입력하세요");
        }
        // 회원 상태 변경
        userEntity.setStatus("activated");
        return userRepository.save(userEntity);
    }

    // 아이디 중복검사
    public boolean checkId(String id) {
        return userRepository.findById(id).isPresent();
    }

    // 로그인
    public UserEntity login(String id, String password) {
        UserEntity user = userRepository.findById(id).get();

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    // 아이디 찾기
    public String getUserId(String name, String email) {
        UserEntity user = userRepository.findByNameAndEmail(name, email);
        return (user != null) ? user.getId() : null;
    }

    // 비밀번호 찾기
    public String getUserPassword(String id, String name, String email) {
        UserEntity user = userRepository.findByIdAndNameAndEmail(id, name, email);
        return (user != null) ? user.getPassword() : null;
    }

    // 사용자 정보 조회
    public UserEntity getUserInfo(String id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("정보 없음");
        }
        return user;
    }

    // 회원정보 수정
    public void editUserInfo(UserEntity userEntity) {
        if (userEntity == null) {
            throw new RuntimeException("엔티티가 잘못되었습니다.");
        }
        userRepository.save(userEntity);
    }

    // 회원정보 수정 (차량 이름, 장치 아이디)
    public UserEntity editCarNameAndDeviceId(UserDTO userDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setCarName(userDTO.getCarName());
            user.setDeviceId(userDTO.getDeviceId());
            return userRepository.save(user);
        }
        return null;
    }

    // 회원 탈퇴
    public String deleteUser(String id, String password) {
        UserEntity user = userRepository.findByIdAndPassword(id, password);
        if (user == null) {
            throw new RuntimeException("입력이 잘못되었습니다.");
        }
        user.setStatus("deactivated");
        userRepository.save(user);
        return "탈퇴 완료.";
    }
}