package com.yoka.common.utils.exception;

import com.yoka.common.errorcode.Code;

public class BizException extends BaseException{

    public BizException(Code code){
        super(code);
    }

    public BizException(Code code,Throwable e){
        super(code,e);
    }

    public BizException(Code code,String msg){
        super(code,msg);
    }

    public BizException(Code code,String msg,Throwable e){
        super(code,msg,e);
    }
}
