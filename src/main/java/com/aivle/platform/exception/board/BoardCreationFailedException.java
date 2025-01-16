package com.aivle.platform.exception.board;

public class BoardCreationFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public BoardCreationFailedException() {
        super("게시판을 찾을 수 없습니다.");
    }

    // 메세지 전달을 위한 생성자
    public BoardCreationFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public BoardCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
