package com.yoka.diff.test;

import com.yoka.common.response.UniqueApiResponse;
import com.yoka.diff.Entity.CodeDiffResultVO;
import com.yoka.diff.controller.CodeDiffController;

import java.util.List;

/**
 * @author:jack
 * @date 2021/6/8 17:15
 * @des:todo
 */
public class TestSendGitRequest {
    public static void main(String[] args) {
        String url = "https://github.com/limeng000111/Diff/";
        String baseVersion = "1";
        String nowVersion = "2";
        try {
            UniqueApiResponse<List<CodeDiffResultVO>> gitList = new CodeDiffController().getGitList(url, baseVersion, nowVersion);
            System.out.println("得到最终结果："+gitList);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
