package com.noaats.eunchae.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("pageTitle", "404 - 페이지를 찾을 수 없습니다");
        return "error/404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("pageTitle", "400 - 잘못된 요청");
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerError(Exception ex, Model model) {
        model.addAttribute("errorMessage", "예상치 못한 오류가 발생했습니다.");
        model.addAttribute("pageTitle", "500 - 서버 오류");
        return "error/500";
    }
}
