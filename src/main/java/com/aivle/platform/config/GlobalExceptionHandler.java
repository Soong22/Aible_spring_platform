package com.aivle.platform.config;

import com.aivle.platform.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        String errorMessage = e.getMessage() != null ? e.getMessage() : "알 수 없는 메시지입니다.";
        model.addAttribute("errorMessage", errorMessage);
        return "error/error";
    }

}