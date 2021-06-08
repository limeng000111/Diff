package com.yoka.common.vercontroller;

import com.yoka.diff.Enum.CodeManageTypeEnum;

/**
 * @author:jack
 * @date 2021/6/8 10:42
 * @des:todo
 */
public class GitAbstractVersionController extends AbstractVersionControl {
    @Override
    public void getDiffCodeClasses() {

    }

    @Override
    public String getLocalNewPath(String filePackage) {
        return null;
    }

    @Override
    public CodeManageTypeEnum getType() {
        return null;
    }

    @Override
    public String getLocalOldPath(String filePackage) {
        return null;
    }
}
