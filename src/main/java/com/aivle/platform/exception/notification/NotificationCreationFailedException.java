package com.aivle.platform.exception.notification;

public class NotificationCreationFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public NotificationCreationFailedException() {
        super("알림 작성에 실패 하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public NotificationCreationFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public NotificationCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
