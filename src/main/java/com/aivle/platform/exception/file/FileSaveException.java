package com.aivle.platform.exception.file;

public class FileSaveException extends RuntimeException {

    // 메세지 전달을 위한 기본 생성자
    public FileSaveException() {
        super("파일을 저장할 수 없습니다.");
    }

    public FileSaveException(String message) {
        super(message);
    }

    // 메시지와 원인(Throwable)을 전달하는 생성자
    public FileSaveException(String message, Throwable cause) {
        super(message, cause);
    }

}
