package com.yoka.diff.Service;

import com.yoka.diff.Entity.ClassInfoResult;
import com.yoka.diff.Entity.DiffMethodParams;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CodeDiffService {

    /**
     * 获取差异代码
     * @param diffMethodParams
     * @return
     */
    List<ClassInfoResult> getDiffCode(DiffMethodParams diffMethodParams);
}
