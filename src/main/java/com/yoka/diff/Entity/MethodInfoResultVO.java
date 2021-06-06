package com.yoka.diff.Entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author:jack
 * @date 2021/6/4 16:50
 * @des:todo
 */
public class MethodInfoResultVO {

    /**
     * 方法名
     */
    @ApiModelProperty(name = "methodName",value = "方法名",dataType = "String",example = "getAll")
    public String methodName;

    /**
     * 方法参数
     */
    @ApiModelProperty(name = "parameters",value = "parameters",dataType = "string")
    public String parameters;
}
