package com.aivle.platform.exception.member;

public class MemberDeletionFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public MemberDeletionFailedException() {
        super("회원 삭제에 실패하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public MemberDeletionFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public MemberDeletionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
