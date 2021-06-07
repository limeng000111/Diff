package com.yoka.diff.Util;

import ch.qos.logback.core.util.FileUtil;
import com.google.common.base.Splitter;
import com.yoka.common.errorcode.BizCode;
import com.yoka.common.utils.File.FileUtils;
import com.yoka.common.utils.exception.BizException;
import com.yoka.diff.config.ExecutorConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.util.Strings;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author:jack
 * @date 2021/6/4 16:58
 * @des:todo
 */
@Slf4j
public class GitRepoUtil {
    private static final Logger logger = LoggerFactory.getLogger(GitRepoUtil.class);

    /**
     * 将git代码克隆到本地
     * @param gitUrl
     * @param codePath
     * @param commitId
     * @param gitUserName
     * @param gitPassWord
     * @return
     */
    public static Git cloneRepository(String gitUrl,String codePath,String commitId,String gitUserName,String gitPassWord){
        Git git = null;
        try {
            if (!checkGitWorkSpace(gitUrl,codePath)){
                logger.info("本地代码不存在");
                git = Git.cloneRepository()
                        .setURI(gitUrl)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName,gitPassWord))
                        .setDirectory(new File(codePath))
                        .setBranch(commitId)
                        .call();
                //下载指定的commitId/branch
                git.checkout().setName(commitId).call();
            }else {
                logger.info("本地存在代码，直接进行pull操作");
                git = Git.open(new File(codePath));
                git.getRepository().getFullBranch();
                if (git.getRepository().exactRef(Constants.HEAD).isSymbolic()){
                    //更新代码
                    git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName,gitPassWord)).call();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return git;
    }

    /**
     * 判断工作目录是否存在
     * @param gitUrl
     * @param codePath
     * @return
     * @throws IOException
     */
    public static Boolean checkGitWorkSpace(String gitUrl,String codePath) throws IOException {
        Boolean flag = false;
        File repoGitRepo = new File(codePath + "/.git");
        if (!repoGitRepo.exists()){
            return flag;
        }
        Git git = Git.open(new File(codePath));
        if (git == null){
            return flag;
        }
        Repository repository = git.getRepository();
        //解析本地代码
        String repoUrl = repository.getConfig().getString("remote", "origin", "url");
        //获取远程url，判断是否需要拉远程仓库
        if (gitUrl.equals(repoUrl)){
            flag = true;
        }else {
            logger.info("存在其他代码，请先删除");
            FileUtils.delDir(new File(codePath));
        }
        return flag;
    }

    /**
     * 将代码转换成树状
     * @param repository
     * @param branch
     * @return
     */
    public static AbstractTreeIterator prepareTreeParse(Repository repository,String branch){
        try {
            RevWalk walk = new RevWalk(repository);
            RevTree tree;
            if (null == repository.resolve(branch)){
                //抛出解析git分支异常
                throw new BizException(BizCode.PARSE_BRANCH_ERROR);
            }
            tree = walk.parseTree(repository.resolve(branch));
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()){
                treeParser.reset(reader,tree.getId());
            }
            walk.dispose();
            return treeParser;
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
        } catch (AmbiguousObjectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取远程代码本地存储路径
     * @param repoUrl
     * @param localBaseRepoUrl
     * @param version
     * @return
     */
    public static String getLocalDir(String repoUrl,String localBaseRepoUrl,String version){
        StringBuilder localDir = new StringBuilder(localBaseRepoUrl);
        if (Strings.isNullOrEmpty(repoUrl)){
            return "";
        }
        localDir.append("/");
        //java8新特性
        String repoName = Arrays.stream(repoUrl.split("/")).reduce((first,second)->first)
                .map(e -> Arrays.stream(e.split(".")).findFirst().get()).get();
        localDir.append(repoName);
        if (!Strings.isNullOrEmpty(version)){
            localDir.append("/");
            localDir.append(version);
        }
        return localDir.toString();
    }
}
