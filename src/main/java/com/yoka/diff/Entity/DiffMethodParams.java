package com.yoka.diff.Entity;

import com.yoka.diff.Enum.CodeManageTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author:jack
 * @date 2021/6/3 17:46
 * @des:todo
 */

@Data
@Builder
public class DiffMethodParams {
    /**
     * 远程仓库
     */
    private String repoUrl;
    /**
     * git原始分支
     */
    private String baseVersion;
    /**
     * git现分支
     */
    private String nowVersion;
    /**
     * 专用svn新分支
     */
    private String svnRepoUrl;

    /**
     * 版本控制类型
     *
     */
    private CodeManageTypeEnum codeManageTypeEnum;

}
