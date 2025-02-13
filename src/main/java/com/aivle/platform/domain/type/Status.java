package com.aivle.platform.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    GENERAL("일반"),
    IMPORTANT("공지사항"),
    PENDING("확인대기"),
    COMPLETED("확인완료");

    private final String description;

}