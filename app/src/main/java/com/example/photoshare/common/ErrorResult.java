package com.example.photoshare.common;

/**
 * 文件名：ErrorResult
 * 作  者： Agonystrly
 * 日  期：2023/8/19 5:09 PM
 * 描述：错误处理
 */
public class ErrorResult<T> {

    /**
     * 业务响应码
     */
    private int code;
    /**
     * 响应提示信息
     */
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
