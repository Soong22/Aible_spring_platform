package com.aivle.platform.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import jakarta.validation.ConstraintViolationException;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    // application-*.yml에서 app.show-stacktrace 값을 읽어옴 (기본 false)
    @Value("${app.show-stacktrace:false}")
    private boolean showStackTrace;

    /**
     * [1] 400 BadRequest (타입 변환 실패)
     */
    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatchException(TypeMismatchException ex,
                                              HttpServletRequest request,
                                              Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", "타입 변환에 실패했습니다: " + ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        // 개발 환경일 때만 스택 트레이스 추가
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [2] 400 BadRequest (필수 요청 파라미터 누락)
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
                                                                HttpServletRequest request,
                                                                Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", "필수 파라미터가 누락되었습니다: " + ex.getParameterName());
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [3] 400 BadRequest (Bean Validation - 바인딩 에러)
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex,
                                      HttpServletRequest request,
                                      Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", "입력 값이 올바르지 않습니다.");
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [4] 400 BadRequest (JSON Body + @Valid 에러)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                        HttpServletRequest request,
                                                        Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", "입력 JSON이 유효하지 않습니다.");
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [5] 400 BadRequest (Bean Validation - ConstraintViolation)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException ex,
                                                     HttpServletRequest request,
                                                     Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", "제약 조건을 위반한 값이 있습니다.");
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [6] 400 BadRequest (IllegalArgumentException)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex,
                                                 HttpServletRequest request,
                                                 Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [7] 404 Not Found (매핑된 핸들러가 없는 경우 - Spring 설정 필요)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                HttpServletRequest request,
                                                Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", "요청하신 페이지를 찾을 수 없습니다.");
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [8] 405 Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
                                                               HttpServletRequest request,
                                                               Model model) {
        model.addAttribute("status", 405);
        model.addAttribute("error", "Method Not Allowed");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [9] 403 Forbidden (Spring Security 인가 실패)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              Model model) {
        model.addAttribute("status", 403);
        model.addAttribute("error", "Forbidden");
        model.addAttribute("message", "접근 권한이 없습니다.");
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [10] 404 (RuntimeException) 예시
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex,
                                         HttpServletRequest request,
                                         Model model) {
        // 예: 특정 RuntimeException을 404로 처리하고 싶다면
        model.addAttribute("status", 404);
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [11] 500 Internal Server Error (그 외 모든 예외)
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,
                                  HttpServletRequest request,
                                  Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        if (showStackTrace) {
            model.addAttribute("trace", getStackTraceAsString(ex));
        }
        return "error/error";
    }

    /**
     * [Optional] 스택 트레이스를 문자열로 변환해주는 헬퍼 메서드
     */
    private String getStackTraceAsString(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
