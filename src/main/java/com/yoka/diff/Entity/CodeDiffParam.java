package com.yoka.diff.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:jack
 * @date 2021/6/4 14:55
 * @des:增量代码请求参数
 */
@Data
@ApiModel("增量代码获取参数")
public class CodeDiffParam {
    /**
     * 远程仓库地址
     */
    @ApiModelProperty(name = "name",value = "远程仓库地址",dataType = "String",example = "https://github.com")
    private String repoUrl;

    /**
     * git原有分支
     */
    @ApiModelProperty(name = "name",value = "原始分支或者tag",dataType = "String",example = "master")
    private String baseVersion;

    @ApiModelProperty(name = "name",value = "现有分支或者tag",dataType = "String",example = "dev")
    private String nowVersion;
}
