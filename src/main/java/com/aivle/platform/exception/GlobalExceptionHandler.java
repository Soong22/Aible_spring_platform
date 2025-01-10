package com.aivle.platform.exception;

import com.aivle.platform.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외를 처리하고 에러 페이지로 리다이렉트
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, Authentication authentication) {
        // 로그에 예외 내용 기록 (필요시)
//        e.printStackTrace(); // 또는 로깅 프레임워크 사용 (예: Logger)

        MemberService.addMemberInfoToModel(model, authentication);

        // 에러 메시지를 모델에 추가
        model.addAttribute("errorMessage", e.getMessage());

        // 에러 페이지로 리다이렉트
        return "error/error"; // "error"는 error.html을 의미
    }

}
