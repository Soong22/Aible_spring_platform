package com.aivle.platform.controller;


import com.aivle.platform.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    /**
     * 이메일 인증번호 전송
     * POST /api/email/send?email=someone@example.com
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestParam("email") String email) {
        Map<String, Object> result = new HashMap<>();
        try {
            emailVerificationService.sendVerificationCode(email);
            result.put("success", true);
            result.put("message", "인증번호가 전송되었습니다.");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "인증번호 전송 실패");
            return ResponseEntity.status(500).body(result);
        }
    }

    /**
     * 인증번호 검증
     * POST /api/email/verify?email=someone@example.com&code=123456
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestParam("email") String email,
                                                          @RequestParam("code") String code) {
        Map<String, Object> result = new HashMap<>();
        boolean verified = emailVerificationService.verifyCode(email, code);

        if (verified) {
            result.put("success", true);
            result.put("message", "인증 완료");
        } else {
            result.put("success", false);
            result.put("message", "인증번호 불일치 혹은 만료");
        }
        return ResponseEntity.ok(result);
    }
}
