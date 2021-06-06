package com.yoka.diff.Entity;

/**
 * @author:jack
 * @date 2021/6/3 17:31
 * @des:todo
 */
public class DiffEntry {
    /**
     * 文件包名
     */
    protected String newPath;
    /**
     * 文件变更类型
     */
    private org.eclipse.jgit.diff.DiffEntry.ChangeType changeType;
}
