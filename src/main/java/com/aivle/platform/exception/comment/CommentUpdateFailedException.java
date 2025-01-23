package com.aivle.platform.exception.comment;

public class CommentUpdateFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public CommentUpdateFailedException() {
        super("게시판 수정에 실패하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public CommentUpdateFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public CommentUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
