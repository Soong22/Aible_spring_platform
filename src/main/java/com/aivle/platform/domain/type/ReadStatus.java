package com.aivle.platform.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReadStatus {
    UNREAD("읽지않음"),
    READ("읽음");

    private final String description;

}