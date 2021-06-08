package com.yoka.common.response;

import com.yoka.common.errorcode.BaseCode;
import com.yoka.common.errorcode.Code;
import com.yoka.common.utils.exception.BizException;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:jack
 * @date 2021/6/8 15:33
 * @des:todo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public ApiResponse(Code err){
        this.code = err.getCode();
        this.msg = err.getFixTips();
    }

    public ApiResponse(BizException ex){
        this.code = ex.getCode();
        this.msg = ex.getMsg();
    }

    public ApiResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse success(){
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getInfo();
        return this;
    }

    public ApiResponse success(T data){
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getInfo();
        this.data = data;
        return this;
    }

    public ApiResponse<T> error(Code code){
        this.code = code.getCode();
        this.msg = code.getInfo();
        this.data = null;
        return this;
    }

    public ApiResponse<T> error(Code code,String msg){
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

    public ApiResponse<T> error(Code code,T data){
        this.code = code.getCode();
        this.msg = code.getInfo();
        this.data = data;
        return this;
    }
}
