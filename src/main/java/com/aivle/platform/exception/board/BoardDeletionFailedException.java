package com.aivle.platform.exception.board;

public class BoardDeletionFailedException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public BoardDeletionFailedException() {
        super("게시판 삭제에 실패하였습니다.");
    }

    // 메세지 전달을 위한 생성자
    public BoardDeletionFailedException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public BoardDeletionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}