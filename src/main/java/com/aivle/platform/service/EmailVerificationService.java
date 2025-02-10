package com.aivle.platform.service;

import com.aivle.platform.domain.VerificationToken;
import com.aivle.platform.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final JavaMailSender mailSender;               // Spring Boot가 자동 구성
    private final VerificationTokenRepository tokenRepo;

    /**
     * 인증번호 생성 후 이메일 전송
     */
    public void sendVerificationCode(String email) {
        // 예: 6자리 난수 (100000 ~ 999999)
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        // 인증 토큰 DB 저장 (만료시간 10분 후로 가정)
        VerificationToken token = new VerificationToken(email, code, LocalDateTime.now().plusMinutes(10));
        tokenRepo.save(token);

        // 메일 작성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 인증번호");
        message.setText("인증번호: " + code + "\n10분 이내에 입력해주세요.");

        // 메일 발송
        mailSender.send(message);
    }

    /**
     * 사용자가 입력한 코드 검증
     */
    public boolean verifyCode(String email, String code) {
        VerificationToken token = tokenRepo.findByEmail(email);
        if (token == null) {
            return false;
        }
        // 코드 일치 & 만료 전이면 인증 성공
        if (token.getCode().equals(code) && token.getExpiryDate().isAfter(LocalDateTime.now())) {
            tokenRepo.delete(token); // 재사용 방지 위해 삭제 (선택)
            return true;
        }
        return false;
    }
}
