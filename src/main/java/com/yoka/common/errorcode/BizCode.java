package com.yoka.common.errorcode;

/**
 * 业务异常类
 */
public enum BizCode implements Code {
    GIT_OPERATED_FAIlED(20000, "git拉取代码失败", "git拉取代码失败"),
    CHARGE_PKG_SKU_INVALID(20001, "包裹商品数据不存在", "请检查包裹商品信息是否存在"),
    CREATE_JOB_FAIL(20002, "创建job失败", "请联系管理员"),
    PARSE_BRANCH_ERROR(20003, "解析分支失败", "请确认分支正常"),
    PARSE_JAVA_FILE(20004, "解析java类失败", "请确认类是否有语法错误"),
    GIT_AUTH_FAILED(20005, "git认证失败", "git认证失败"),
    LOAD_CLASS_FAIL(20006, "读取java类失败", "读取java类失败，请稍后再试")
    ;
    private int code;
    private String info;
    private String fixTips;
    BizCode(int code,String info,String fixTips){
        this.code = code;
        this.info = info;
        this.fixTips = fixTips;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public String getFixTips() {
        return this.fixTips;
    }
}
