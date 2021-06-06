package com.yoka.diff.Entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author:jack
 * @date 2021/6/3 17:23
 * @des:todo
 */
@Builder
@Data
public class MethodInfoResult {
    /**
     * 方法的md5
     */
    public String md5;
    /**
     * 方法名
     */
    public String methodName;
    /**
     * 方法参数
     */
    public String parameters;
}
