package com.cfs.obd2logger.controller;

import com.cfs.obd2logger.dto.UserDTO;
import com.cfs.obd2logger.entity.UserEntity;
import com.cfs.obd2logger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // 회원가입

    // 아이디 중복체크
    @PostMapping("")
    public ResponseEntity<?> duplicateCheckId(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(userService.checkId(userDTO.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인

    // 아이디 찾기
    @GetMapping("")
    public ResponseEntity<String> findUserId(@RequestParam String name, @RequestParam String email) {
        String userId = userService.getUserId(name, email);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("정보가 잘못되었습니다");
        }
    }

    // 비밀번호 찾기 및 수정

    // 사용자 정보 조회
    @GetMapping("")
    public ResponseEntity<?> getUserProfile(@RequestParam String id) {
        try {
            UserEntity user = userService.getUserInfo(id);

            UserDTO responseUser = UserDTO.builder() // 수정 필요
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
            return ResponseEntity.ok().body(responseUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 회원정보 수정 (비밀번호, 차종, 제품 일련번호) -> 토큰

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