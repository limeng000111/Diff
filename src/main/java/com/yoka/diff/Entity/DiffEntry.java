package com.yoka.diff.Entity;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import lombok.Data;

/**
 * @author:jack
 * @date 2021/6/3 17:31
 * @des:todo
 */
@Data
public class DiffEntry {
    /**
     * 文件包名
     */
    protected String newPath;
    /**
     * 文件变更类型
     */
    public ChangeType changeType;
}
