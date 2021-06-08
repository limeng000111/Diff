package com.yoka.diff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yoka.common.response.UniqueApiResponse;
import com.yoka.common.utils.mapper.OrikaMapperUtils;
import com.yoka.diff.Entity.ClassInfoResult;
import com.yoka.diff.Entity.CodeDiffResultVO;
import com.yoka.diff.Entity.DiffMethodParams;
import com.yoka.diff.Enum.CodeManageTypeEnum;
import com.yoka.diff.Service.CodeDiffService;
import com.yoka.diff.Service.Imp.CodeDiffServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:jack
 * @date 2021/6/8 16:04
 * @des:todo
 */
@RestController
@RequestMapping("/api/code/diff")
public class CodeDiffController {

    @Autowired
    private CodeDiffServiceImp codeDiffService;

    @RequestMapping(value = "/git/list",method = RequestMethod.GET)
    public UniqueApiResponse<List<CodeDiffResultVO>> getGitList(
            @RequestParam(value = "gitUrl") String url,
            @RequestParam(value = "baseVersion") String baseVersion,
            @RequestParam(value = "nowVersion") String nowVersion){
        DiffMethodParams build = DiffMethodParams.builder()
                .baseVersion(baseVersion)
                .nowVersion(nowVersion)
                .codeManageTypeEnum(CodeManageTypeEnum.GIT)
                .repoUrl(url)
                .build();
        List<ClassInfoResult> diffCode = new CodeDiffServiceImp().getDiffCode(build);
        List<ClassInfoResult> classInfoResults = OrikaMapperUtils.mapList(diffCode, ClassInfoResult.class, ClassInfoResult.class);
        UniqueApiResponse uniqueApiResponse = new UniqueApiResponse();
        return uniqueApiResponse.success(classInfoResults,JSON.toJSONString(classInfoResults));
    }
}
