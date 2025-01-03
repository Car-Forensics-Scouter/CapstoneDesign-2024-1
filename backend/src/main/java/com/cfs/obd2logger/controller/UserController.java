package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.User;
import com.cfs.obd2logger.security.TokenProvider;
import com.cfs.obd2logger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User user = User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .carName(userDTO.getCarName())
            .id(userDTO.getId())
            .deviceId(userDTO.getDeviceId())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .status("activated") // 회원가입 시 활성 상태로 설정
            .build();

        User responseUser = userService.signup(user);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    // 아이디 중복검사
    @PostMapping("/check-id")
    public ResponseEntity<?> duplicateCheckId(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.checkId(userDTO.getId()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO.getId(), userDTO.getPassword());

        if (user == null) {
            throw new RuntimeException("로그인 실패");
        }

        // 사용자 상태가 "deactivated"인 경우 로그인 불가
        if ("deactivated".equals(user.getStatus())) {
            throw new RuntimeException("비활성화된 계정입니다.");
        }

        String token = tokenProvider.create(user);

        UserDTO responseUserDTO = UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .carName(user.getCarName())
            .deviceId(user.getDeviceId())
            .token(token)
            .build();
        return ResponseEntity.ok().body(responseUserDTO);
    }

    // 아이디 찾기
    @GetMapping("find_id")
    public ResponseEntity<String> findUserId(@RequestParam String name,
        @RequestParam String email) {
        String userId = userService.getUserId(name, email);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디가 존재하지 않습니다.");
        }
    }

    // 비밀번호 재발급
    @GetMapping("find_password")
    public ResponseEntity<String> generateTempPassword(@RequestParam String id,
        @RequestParam String name, @RequestParam String email) {
        String userPassword = userService.getUserPassword(id, name, email);
        if (userPassword != null) {
            return ResponseEntity.ok(userPassword);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 계정입니다.");
        }
    }

    // 회원정보 수정 (비밀번호, 차종, 제품 일련번호)
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO userDTO) {
        String newPassword = passwordEncoder.encode(userDTO.getPassword()); // 비밀번호 암호화

        User updateUser = userService.editPassword(userDTO, newPassword);
        if (updateUser == null) {
            return ResponseEntity.ok().body("존재하지 않는 유저입니다.");
        }
        String token = tokenProvider.create(updateUser);

        UserDTO responseUserDTO = UserDTO.builder()
            .id(updateUser.getId())
            .password(newPassword)
            .token(token)
            .build();
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PatchMapping("/carName")
    public ResponseEntity<?> updateCarName(@RequestBody UserDTO userDTO) {
        User updateUser = userService.editCarName(userDTO);
        if (updateUser == null) {
            return ResponseEntity.ok().body("존재하지 않는 유저입니다.");
        }
        String token = tokenProvider.create(updateUser);

        UserDTO responseUserDTO = UserDTO.builder()
            .id(updateUser.getId())
            .carName(updateUser.getCarName())
            .token(token)
            .build();
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PatchMapping("/deviceId")
    public ResponseEntity<?> updateDeviceId(@RequestBody UserDTO userDTO) {
        User updateUser = userService.editDeviceId(userDTO);
        if (updateUser == null) {
            return ResponseEntity.ok().body("존재하지 않는 유저입니다.");
        }
        String token = tokenProvider.create(updateUser);

        UserDTO responseUserDTO = UserDTO.builder()
            .id(updateUser.getId())
            .deviceId(updateUser.getDeviceId())
            .token(token)
            .build();
        return ResponseEntity.ok().body(responseUserDTO);
    }
}