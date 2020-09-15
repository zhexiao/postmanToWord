package com.zhexiao.convert.exceptions;

/**
 *
 * 自定义异常类
 *
 * Spring对于RuntimeException类的异常才会进行事务回滚
 * 所以我们一般自定义异常都继承该异常类
 *
 * @author zhexiao
 * @date 2020-06-25 22:45
 **/
public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}