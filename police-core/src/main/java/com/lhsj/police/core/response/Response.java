package com.lhsj.police.core.response;

import java.io.Serializable;

import static com.lhsj.police.core.constant.Constants.CODE_ERROR;
import static com.lhsj.police.core.constant.Constants.CODE_SUCCESS;
import static com.lhsj.police.core.constant.Constants.MSG_ERROR;
import static com.lhsj.police.core.constant.Constants.MSG_SUCCESS;

public class Response<T> implements Serializable {

    private boolean success;

    private String code;

    private String msg;

    private T data;

    public Response() {
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public Response(boolean success, String code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // ----------- flow api -----------

    public static Response<?> of() {
        return new Response<>();
    }


    public static <T> Response<T> ofSuccess() {
        return new Response<>(true, CODE_SUCCESS, MSG_SUCCESS);
    }

    public static <T> Response<T> ofError() {
        return new Response<>(false, CODE_ERROR, MSG_ERROR);
    }

    public Response<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public Response<T> success() {
        this.success = true;
        return this;
    }

    public Response<T> error() {
        this.success = false;
        return this;
    }

    public Response<T> code(String code) {
        this.code = code;
        return this;
    }

    public Response<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Response<T> data(T data) {
        this.data = data;
        return this;
    }

}
