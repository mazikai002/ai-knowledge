package com.itheima.backend.common.exception;

import com.itheima.backend.common.Result;
import com.itheima.backend.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(Result.error(e.getCode(), e.getMessage()));
    }
    
    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.internalServerError()
                .body(Result.error(CommonConstant.ERROR_MESSAGE));
    }
} 