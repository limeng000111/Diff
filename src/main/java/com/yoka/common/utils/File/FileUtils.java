package com.yoka.common.utils.File;

import com.yoka.diff.Util.GitRepoUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author:jack
 * @date 2021/6/4 17:36
 * @des:处理文件
 */
@Slf4j
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static void delDir(File dir){
        if (dir == null){
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                delDir(file);
            }else {
                file.delete();
            }
        }
        logger.info("dir:"+dir+"已删除");
    }
}
