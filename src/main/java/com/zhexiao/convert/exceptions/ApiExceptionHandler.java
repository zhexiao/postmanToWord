package com.zhexiao.convert.exceptions;

import com.zhexiao.convert.base.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 指定拦截异常的类型,被捕获的异常会调用handler方法，方法名自己随便定
 *
 * @author zhexiao
 * @date 2020-06-25 22:48
 **/
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public Result handler(ApiException e) {
        return Result.failed(e.getMessage());
    }
}