package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.repository.UserRepository;
import jakarta.persistence.Entity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

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
        Optional<UserEntity> responseUser = userRepository.findById(id);

        if (responseUser.isPresent()) {
            UserEntity user = responseUser.get();
            System.out.println("객체가 존재합니다");
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                System.out.println("비밀번호가 잘못되엇습니다.");
            }
        }
        else{
            System.out.println("해당 id에 해당하는 사용자가 존재하지 않습니다.");
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