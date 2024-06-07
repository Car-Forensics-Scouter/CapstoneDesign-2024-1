package com.cfs.obd2logger.service;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.User;
import com.cfs.obd2logger.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${RANDOM_CHARACTERS}")
    private String characters;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    // 회원 가입
    public User signup(User user) {
        if (user == null) {
            throw new RuntimeException("정보를 모두 입력하세요");
        }
        // 회원 상태 변경
        user.setStatus("activated");
        return userRepository.save(user);
    }

    // 아이디 중복검사
    public boolean checkId(String id) {
        return userRepository.findById(id).isPresent();
    }

    // 로그인
    public User login(String id, String password) {
        Optional<User> responseUser = userRepository.findById(id);

        if (responseUser.isPresent()) {
            User user = responseUser.get();
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
        User user = userRepository.findByNameAndEmail(name, email);
        return (user != null) ? user.getId() : null;
    }

    // 비밀번호 재발급
    public String getUserPassword(String id, String name, String email) {
        User user = userRepository.findByIdAndNameAndEmail(id, name, email);
        if (user != null) {
            String newTempPassword = generateRandomPassword();
            user.setPassword(passwordEncoder.encode(newTempPassword));
            userRepository.save(user);
            return newTempPassword;
        } else {
            return null;
        }
    }

    private String generateRandomPassword() {
        // 비밀번호 길이
        int length = 10;

        // 임시 비밀번호 생성
        StringBuilder newPassword = new StringBuilder();

        // 랜덤한 문자열 선택
        for (int i = 0; i < length; i++) {
            int index = (int)(Math.random() * characters.length());
            newPassword.append(characters.charAt(index));
        }

        return newPassword.toString();
    }

    // 회원정보 수정
    public User editPassword(UserDTO userDTO, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword); // 암호화된 비밀번호를 설정
            return userRepository.save(user);
        }
        return null;
    }

    public User editCarName(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCarName(userDTO.getCarName());
            return userRepository.save(user);
        }
        return null;
    }

    public User editDeviceId(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeviceId(userDTO.getDeviceId());
            return userRepository.save(user);
        }
        return null;
    }
}