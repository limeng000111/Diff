package com.yoka.common.utils.exception;

import com.yoka.common.errorcode.Code;

/**
 * @author:jack
 * @date 2021/6/4 18:07
 * @des:todo
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    public BaseException(Code code){
        super(code.getFixTips());
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public BaseException(Code code,Throwable e){
        super(code.getFixTips(),e);
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public BaseException(Code code,String msg){
        super(msg);
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public BaseException(Code code,String msg,Throwable e){
        super(msg,e);
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

    public BaseException(){
        super();
    }
}
