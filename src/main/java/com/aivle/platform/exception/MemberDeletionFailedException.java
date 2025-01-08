package com.aivle.platform.exception;

public class MemberDeletionFailedException extends RuntimeException {
    // 메시지 전달을 위한 생성자
    public MemberDeletionFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public MemberDeletionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
