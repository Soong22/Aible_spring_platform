package com.aivle.platform.exception.comment;

public class CommentCreationFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public CommentCreationFailedException() {
        super("댓글 작성에 실패하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public CommentCreationFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public CommentCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
