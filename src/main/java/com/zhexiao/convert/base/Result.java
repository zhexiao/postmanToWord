package com.zhexiao.convert.base;

import lombok.Data;

/**
 * 返回类
 *
 * @author zhexiao
 * @date  2020-07-22
 **/
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "ok", data);
    }

    /**
     * 失败返回
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(400, message, null);
    }
}