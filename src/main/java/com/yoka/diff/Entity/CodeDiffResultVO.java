package com.yoka.diff.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
 * @author:jack
 * @date 2021/6/4 15:01
 * @des:差异代码结果集
 */
@Data
@ApiModel("差异代码结果集")
public class CodeDiffResultVO {

    /**
     * 模块名称
     */
    @ApiModelProperty(name = "moudleName", value = "模块名",dataType = "String",example = "common")
    private String moduleName;

    /**
     * java文件
     */
    @ApiModelProperty(name = "classFile", value = "java文件名",dataType = "String", example = "com.yoka.diff.config.Executor.java")
    private String classFile;

    /**
     * 类中的方法
     */
    @ApiModelProperty(name = "methInfos", value = "类中的方法")
    private List<MethodInfoResult> methInfos;

    @ApiModelProperty(name = "type", value = "修改类型", dataType = "String",example = "ADD")
    private String type;

}
