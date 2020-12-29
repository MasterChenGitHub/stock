package com.master.stock.response;

import java.io.Serializable;

/**
 * @param <T>
 * @author he
 * @ClassName: BaseResponse
 * @Description: API接口返回通用消息体
 * @date Mar 13, 2020
 */
public class BaseResponse<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6976459601257886981L;

    /**
     * 状态： ok、failed
     */
    private String status;

    /**
     * HTTP状态码
     */
    private String code;

    /**
     * 状态码消息
     */
    private String message;

    /**
     * 实体数据
     */
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 无效cookies
     */
    public void getInvalidCookiesError() {
        this.setStatus("failed");
        this.setMessage("无效Cookie");
        this.setCode("404");
    }

    /**
     * 未查询到
     */
    public void notfound() {
        this.setStatus("failed");
        this.setMessage("not found");
        this.setCode("404");
    }

    /**
     * 无效参数
     */
    public void getInvalidParams() {
        this.setStatus("failed");
        this.setMessage("无效参数");
        this.setCode("404");
    }

    /**
     * 请求成功
     */
    public void success() {
        this.setStatus("ok");
        this.setMessage("请求成功");
        this.setCode("200");
    }

    /**
     * 请求失败
     *
     * @param message
     */
    public void fail(String message) {
        this.setStatus("failed");
        this.setMessage("请求失败,原因:" + message);
        this.setCode("500");
    }

    /**
     * 服务器发生内部错误
     *
     * @return
     */
    public void getInternalError() {
        this.setStatus("failed");
        this.setMessage("服务器发生内部错误");
        this.setCode("500");
    }

}
