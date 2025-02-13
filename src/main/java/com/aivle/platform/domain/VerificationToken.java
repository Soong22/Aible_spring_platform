package com.aivle.platform.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationTokenId;

    private String email;              // 인증할 이메일
    private String code;               // 인증번호 (6자리 등)
    private LocalDateTime expiryDate;  // 만료 시간

    public VerificationToken(String email, String code, LocalDateTime expiryDate) {
        this.email = email;
        this.code = code;
        this.expiryDate = expiryDate;
    }

}
