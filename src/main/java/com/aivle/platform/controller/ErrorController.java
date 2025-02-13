package com.aivle.platform.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
@RequestMapping("/error")
public class ErrorController {

    // application-*.yml에서 app.show-stacktrace 값을 읽어옴 (기본 false)
    @Value("${app.show-stacktrace:false}")
    private boolean showStackTrace;

//    @GetMapping("")
//    public String errorForm(Model model, HttpServletRequest request) {
//        // 🚀 에러 정보를 가져오기
//        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
//        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
//        String path = (String) request.getAttribute("javax.servlet.error.request_uri");
//
//        model.addAttribute("status", status != null ? status : 500); // 기본값 500
//        model.addAttribute("error", "서버 오류");
//        model.addAttribute("message", errorMessage != null ? errorMessage : "예기치 못한 오류가 발생했습니다.");
//        model.addAttribute("path", path);
//
//        if (showStackTrace && (exception != null)) {
//            model.addAttribute("trace", getStackTraceAsString(exception)); // 스택 트레이스 추가
//        }
//
//        return "error/error"; // Thymeleaf 템플릿 반환
//    }

    @GetMapping("/403")
    public String error403Form(Model model, HttpServletRequest request) {
        model.addAttribute("status", 403);
        model.addAttribute("error", "Forbidden");
        model.addAttribute("message", "🚫 접근 권한이 없습니다.");
        model.addAttribute("path", request.getRequestURI());

        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (showStackTrace && (exception != null)) {
            model.addAttribute("trace", getStackTraceAsString(exception)); // 스택 트레이스 추가
        }

        return "error/error";
    }

    private String getStackTraceAsString(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

}
