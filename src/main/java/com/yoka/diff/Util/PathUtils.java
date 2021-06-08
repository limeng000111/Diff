package com.yoka.diff.Util;

import org.eclipse.jgit.util.StringUtils;

/**
 * @author:jack
 * @date 2021/6/7 14:40
 * @des:todo
 */
public class PathUtils {
    /**
     * 获取本地类路径
     * @param baseDir
     * @param version
     * @param classPath
     * @return
     */
    public static String getClassFilePath(String baseDir,String version,String classPath){
        StringBuilder builder = new StringBuilder(baseDir);
        if (!StringUtils.isEmptyOrNull(version)){
            builder.append("/");
            builder.append(version);
        }
        builder.append(classPath);
        return builder.toString();
    }
}
