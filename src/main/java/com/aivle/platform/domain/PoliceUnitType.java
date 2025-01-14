package com.aivle.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoliceUnitType {
    ADMIN("관리자"),
    DISTRICT("지구대"),
    OUTPOST("파출소");

    private final String description;

}
