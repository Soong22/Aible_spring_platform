package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Role;

import java.time.LocalDateTime;


public class MemberResponseDto {
    private Long memberId;
    private String memberName;
    private String phoneNumber;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
