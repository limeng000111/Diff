package com.yoka.diff.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author:jack
 * @date 2021/6/4 11:34
 * @des:自定义配置
 */

@Data
@Configuration
public class CustomizeConfig {

    /**
     * git账号
     */
    @Value("${spring.git.userName}")
    private String gitUserName;

    /**
     * git密码
     */
    @Value("${spring.git.password}")
    private String gitPassWord;

    /**
     * git下载代码到本地的目录
     */
    @Value("${spring.git.local.base.dir}")
    private String gitLocalBaseRepoDir;

    /**
     * svn账号
     */
    @Value("${spring.svn.userName}")
    private String svnUserName;

    /**
     * svn密码
     */
    @Value("${spring.svn.password")
    private String svnPassWord;

    /**
     * svn下载代码到本地的目录
     */
    @Value("${spring.svn.local.base.dir}")
    private String svnLocalBaseRepoDir;
}
