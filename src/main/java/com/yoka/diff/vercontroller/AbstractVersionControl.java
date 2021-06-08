package com.yoka.diff.vercontroller;

import com.yoka.diff.Entity.ClassInfoResult;
import com.yoka.diff.Entity.DiffEntry;
import com.yoka.diff.Entity.MethodInfoResult;
import com.yoka.diff.Entity.VersionController;
import com.yoka.diff.Enum.CodeManageTypeEnum;
import com.yoka.diff.Util.MethodPraseUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author:jack
 * @date 2021/6/7 15:03
 * @des:todo
 */
@Data
@Slf4j
public abstract class AbstractVersionControl {

    protected VersionController versionController;

    @Resource
    private Executor executor;

    /**
     * 执行handler
     * @param versionController
     * @return
     */
    public List<ClassInfoResult> handler(VersionController versionController){
        this.versionController = versionController;
        getDiffCodeClasses();
        return getDiffCodeMethods();
    }

    public abstract void getDiffCodeClasses();
    /**
     * 获取旧版本本地路径
     * @param filePackage
     * @return
     */
    public abstract String getLocalNewPath(String filePackage);

    /**
     * 请求类型
     * @return
     */
    public abstract CodeManageTypeEnum getType();

    /**
     * 获取新版本本地路径
     * @param filePackage
     * @return
     */
    public abstract String getLocalOldPath(String filePackage);

    /**
     * 获取增量的方法
     * @param oldClassFile
     * @param newClassFile
     * @param diffEntry
     * @return
     */
    private CompletableFuture<ClassInfoResult> getClassMethods(String oldClassFile, String newClassFile, DiffEntry diffEntry){
        //多线程遍历所有方法
        return CompletableFuture.supplyAsync(()->{
            String className = diffEntry.getNewPath().split("\\.")[0].split("src/main/java/")[1];
            String moudleName = diffEntry.getNewPath().split("/")[0];
            //新增直接标记，不计算方法
            if (org.eclipse.jgit.diff.DiffEntry.ChangeType.ADD.equals(diffEntry.getChangeType())){
                return ClassInfoResult.builder()
                        .classFile(className)
                        .type(org.eclipse.jgit.diff.DiffEntry.ChangeType.ADD.name())
                        .moduleName(moudleName)
                        .build();
            }
            List<MethodInfoResult> diffMethods;
            //获取新类所有方法
            List<MethodInfoResult> newMethodResultInfo = MethodPraseUtil.parseMethods(newClassFile);
            if (CollectionUtils.isEmpty(newMethodResultInfo)){
                return null;
            }
            List<MethodInfoResult> oldMethodResultInfo = MethodPraseUtil.parseMethods(oldClassFile);
            if (CollectionUtils.isEmpty(oldMethodResultInfo)){
                //旧类文件夹为空，新类所有的方法都是增量
                diffMethods = newMethodResultInfo;
            }else {
                //计算增量方法
                List<String> md5s = oldMethodResultInfo.stream().map(MethodInfoResult::getMd5).collect(Collectors.toList());
                diffMethods = newMethodResultInfo.stream().filter(n -> !md5s.contains(n.getMd5())).collect(Collectors.toList());
            }
            if (CollectionUtils.isEmpty(diffMethods)){
                return null;
            }
            return ClassInfoResult.builder()
                    .classFile(className)
                    .methodInfos(diffMethods)
                    .moduleName(moudleName)
                    .type(org.eclipse.jgit.diff.DiffEntry.ChangeType.MODIFY.name())
                    .build();
        },executor);
    }

    /**
     * 获取差异方法
     * @return
     */
    public List<ClassInfoResult> getDiffCodeMethods(){
        if (CollectionUtils.isEmpty(versionController.getDiffClass())){
            return null;
        }
        log.info("需要对比的差异类数："+versionController.getDiffClass().size());
        List<CompletableFuture<ClassInfoResult>> collect = versionController.getDiffClass().stream()
                .map(item -> getClassMethods(getLocalOldPath(item.getNewPath()), getLocalNewPath(item.getNewPath()), item))
                .collect(Collectors.toList());
        CompletableFuture.allOf(collect.toArray(new CompletableFuture[0])).join();
        List<ClassInfoResult> results = collect.stream().map(CompletableFuture::join).filter(Objects::isNull).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(results)){
            log.info("最终差异类数："+ results.size());
        }
        return results;
    }


}
