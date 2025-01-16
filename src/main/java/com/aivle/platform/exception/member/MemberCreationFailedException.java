package com.aivle.platform.exception.member;

public class MemberCreationFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public MemberCreationFailedException() {
        super("회원 가입에 실패하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public MemberCreationFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public MemberCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
