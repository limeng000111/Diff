package com.yoka.diff.vercontroller;

import com.yoka.common.utils.mapper.OrikaMapperUtils;
import com.yoka.diff.Enum.CodeManageTypeEnum;
import com.yoka.diff.Util.GitRepoUtil;
import com.yoka.diff.Util.PathUtils;
import com.yoka.diff.config.CustomizeConfig;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:jack
 * @date 2021/6/8 10:42
 * @des:todo
 */
public class GitAbstractVersionController extends AbstractVersionControl {

    private static final Logger logger = LoggerFactory.getLogger(GitAbstractVersionController.class);

    @Autowired
    CustomizeConfig customizeConfig;

    @Override
    public void getDiffCodeClasses() {
        try {
            String localBaseDir = GitRepoUtil.getLocalDir(super.versionController.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), super.versionController.getBaseVersion());
            String localNowDir = GitRepoUtil.getLocalDir(super.versionController.getRepoUrl(),customizeConfig.getGitLocalBaseRepoDir(),super.versionController.getNowVersion());
            //原有代码对象
            Git baseGit = GitRepoUtil.cloneRepository(super.versionController.getRepoUrl(), localBaseDir, super.versionController.getBaseVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
            //现有代码对象
            Git nowGit = GitRepoUtil.cloneRepository(super.versionController.getRepoUrl(), localNowDir, super.versionController.getNowVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
            AbstractTreeIterator abstractTreeIteratorOld = GitRepoUtil.prepareTreeParse(baseGit.getRepository(), super.versionController.getBaseVersion());
            AbstractTreeIterator abstractTreeIteratorNew = GitRepoUtil.prepareTreeParse(nowGit.getRepository(), super.versionController.getNowVersion());
            //获取两个版本的代码差异
            List<DiffEntry> diff = null;
            diff = nowGit.diff().setOldTree(abstractTreeIteratorOld).setNewTree(abstractTreeIteratorNew).setShowNameAndStatusOnly(true).call();
            List<DiffEntry> validDiffList = diff.stream()
                    //过滤java文件
                    .filter(e -> e.getNewPath().endsWith(".java"))
                    //过滤测试文件
                    .filter(e -> e.getNewPath().contains("/src/main/java"))
                    //过滤ADD和MODIFY文件
                    .filter(e -> DiffEntry.ChangeType.ADD.equals(e.getChangeType()) || DiffEntry.ChangeType.MODIFY.equals(e.getChangeType()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(validDiffList)){
                List<com.yoka.diff.Entity.DiffEntry> diffEntries = OrikaMapperUtils.mapList(validDiffList, DiffEntry.class, com.yoka.diff.Entity.DiffEntry.class);
                super.versionController.setDiffClass(diffEntries);
            }else {
                logger.info("没有需要对比的类");
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取新版本本地路径
     * @param filePackage
     * @return
     */
    @Override
    public String getLocalNewPath(String filePackage) {
        String localDir = GitRepoUtil.getLocalDir(super.versionController.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), "");
        return PathUtils.getClassFilePath(localDir,versionController.getNowVersion(),filePackage);
    }

    /**
     * 获取操作类型
     * @return
     */
    @Override
    public CodeManageTypeEnum getType() {
        return CodeManageTypeEnum.GIT;
    }

    /**
     * 获取老版本本地路径
     * @param filePackage
     * @return
     */
    @Override
    public String getLocalOldPath(String filePackage) {
        String localDir = GitRepoUtil.getLocalDir(super.versionController.getRepoUrl(),customizeConfig.getGitLocalBaseRepoDir(),"");
        return PathUtils.getClassFilePath(localDir,versionController.getBaseVersion(),filePackage);
    }
}
