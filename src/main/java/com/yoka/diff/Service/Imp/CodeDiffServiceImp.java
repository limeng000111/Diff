package com.yoka.diff.Service.Imp;

import com.yoka.common.utils.mapper.OrikaMapperUtils;
import com.yoka.diff.Entity.ClassInfoResult;
import com.yoka.diff.Entity.DiffMethodParams;
import com.yoka.diff.Entity.VersionController;
import com.yoka.diff.Service.CodeDiffService;
import com.yoka.diff.vercontroller.VersionControllerHandleFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:jack
 * @date 2021/6/8 15:20
 * @des:todo
 */
@Component
public class CodeDiffServiceImp implements CodeDiffService {
    @Override
    public List<ClassInfoResult> getDiffCode(DiffMethodParams diffMethodParams) {
        VersionController map = OrikaMapperUtils.map(diffMethodParams, VersionController.class);
        return VersionControllerHandleFactory.processHandler(map);
    }
}
