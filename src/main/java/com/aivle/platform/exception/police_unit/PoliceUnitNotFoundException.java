package com.aivle.platform.exception.police_unit;

public class PoliceUnitNotFoundException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public PoliceUnitNotFoundException() {
        super("지구대/파출소 정보를 찾을 수 없습니다.");
    }

    // 메세지 전달을 위한 생성자
    public PoliceUnitNotFoundException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public PoliceUnitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
