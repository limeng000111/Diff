package com.yoka.diff.Entity;

import com.yoka.diff.Enum.CodeManageTypeEnum;
import lombok.Data;

/**
 * @author:jack
 * @date 2021/6/4 9:41
 * @des:todo
 */
@Data
public class VersionController {

    /**
     * 远程仓库地址
     */
    private String repoUrl;
    /**
     * git原始分支
     */
    private String baseVersion;
    /**
     * git现在分支
     */
    private String nowVersion;
    /**
     * svn新分支
     */
    private String svnRepoURL;
    /**
     * 版本控制类型
     */
    private CodeManageTypeEnum codeManageTypeEnum;

    private DiffEntry diffEntry;

}
