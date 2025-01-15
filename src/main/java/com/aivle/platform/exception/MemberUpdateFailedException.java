package com.aivle.platform.exception;

public class MemberUpdateFailedException extends RuntimeException {

    // 메시지 전달을 위한 생성자
    public MemberUpdateFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public MemberUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}

