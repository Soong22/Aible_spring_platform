package com.aivle.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    GENERAL("일반"),
    PENDING("확인"),
    COMPLETED("확인요청");

    private final String description;

}