package com.yoka.diff.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author:jack
 * @date 2021/6/3 14:11
 * @des:todo
 */
@Builder
@Data
public class ClassInfoResult {
    /**
     * java文件
     */
    private String classFile;
    /**
     * 类名
     */
    private String className;
    /**
     * 包名
     */
    private String packages;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 类中的方法
     */
    private List<MethodInfoResult> methodInfos;
    /**
     * 新增的行数
     */
    private List<int[]> addLines;
    /**
     * 删除的行数
     */
    private List<int[]> delLines;
    /**
     * 修改类型
     */
    private String type;
}
