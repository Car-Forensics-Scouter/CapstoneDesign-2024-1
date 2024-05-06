package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.security.TokenProvider;
import com.cfs.obd2logger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .carName(userDTO.getCarName())
                .id(userDTO.getId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .status("activated") // 회원가입 시 활성 상태로 설정
                .build();

            UserEntity responseUser = userService.signup(user);

            return ResponseEntity.ok().body("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 아이디 중복검사
    @PostMapping("/check-id")
    public ResponseEntity<?> duplicateCheckId(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(userService.checkId(userDTO.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = userService.login(userDTO.getId(), userDTO.getPassword());

            if(user == null) {
                throw new RuntimeException("로그인 실패");
            }

            // 사용자 상태가 "deactivated"인 경우 로그인 불가
            if ("deactivated".equals(user.getStatus())) {
                throw new RuntimeException("비활성화된 계정입니다.");
            }

            String token = tokenProvider.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .token(token)
                .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 아이디 찾기
    @GetMapping("")
    public ResponseEntity<String> findUserId(@RequestParam String name, @RequestParam String email) {
        String userId = userService.getUserId(name, email);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디가 존재하지 않습니다.");
        }
    }

    // 비밀번호 찾기

    // 사용자 정보 조회
    @GetMapping("")
    public ResponseEntity<?> getUserProfile(@RequestParam String id) {
        try {
            UserEntity user = userService.getUserInfo(id);

            UserDTO responseUser = UserDTO.builder()
                .name(user.getName())
                .carName(user.getCarName())
                .build();
            return ResponseEntity.ok().body(responseUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 회원정보 수정 (비밀번호, 차종, 제품 일련번호)
    @PatchMapping("")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = UserEntity.builder()
                .id(userDTO.getId())
                .carName(userDTO.getCarName())
                .deviceId(userDTO.getDeviceId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .status("activated")
                .build();
            userService.editUserInfo(user);

            String token = tokenProvider.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                .id(user.getId())
                .carName(user.getCarName())
                .deviceId(user.getDeviceId())
                .token(token)
                .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 회원 정보 수정 (비밀번호, 차종)
    // 회원 정보 수정 (비밀번호, 일련번호)
    // 회원 정보 수정 (차총, 제품 일련번호)
    // 회원 정보 수정 (비밀번호)
    // 회원 정보 수정 (차총)
    // 회원 정보 수정 (일련번호)

    // 회원 탈퇴
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        try {
            String msg = userService.deleteUser(userDTO.getId(), userDTO.getPassword());
            return ResponseEntity.ok().body(msg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}