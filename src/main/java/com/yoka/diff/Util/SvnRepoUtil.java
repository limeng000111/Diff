package com.yoka.diff.Util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.util.StringUtils;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.Arrays;

/**
 * @author:jack
 * @date 2021/6/7 14:44
 * @des:todo
 */
public class SvnRepoUtil {

    public static void cloneRepository(String repoUrl, String codePath, SVNRevision svnRevision, String userName, String password) {
        try {
            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
            SVNUpdateClient updateClient = SVNClientManager.newInstance((DefaultSVNOptions) options, userName, password).getUpdateClient();
            updateClient.doCheckout(SVNURL.parseURIEncoded(repoUrl), new File(codePath), svnRevision,svnRevision, SVNDepth.INFINITY, false);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }


    public static SVNDiffClient getSVNDiffClient(String userName, String password) {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //实例化客户端管理类
        return SVNClientManager.newInstance((DefaultSVNOptions) options, userName, password).getDiffClient();
    }

    /**
     * 取远程代码本地存储路径
     *
     * @param repoUrl
     * @param localBaseRepoDir
     * @param version
     * @return
     */
    public static String getSvnLocalDir(String repoUrl, String localBaseRepoDir, String version) {
        StringBuilder localDir = new StringBuilder(localBaseRepoDir);
        if (Strings.isNullOrEmpty(repoUrl)) {
            return "";
        }
        localDir.append("/");
//        String repoName = Splitter.on("/")
//                .splitToStream(repoUrl).reduce((first, second) -> second).get();
        String repoName = Arrays.stream(repoUrl.split("/")).reduce((first,second) -> second).get();
        localDir.append(repoName);
        if(!StringUtils.isEmpty(version)){
            localDir.append("/");
            localDir.append(version);
        }
        return localDir.toString();
    }

}
