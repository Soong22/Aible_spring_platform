package com.aivle.platform.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("사용자"),
    ADMIN("관리자"),
    WITHDRAWN("탈퇴자");

    private final String description;

}