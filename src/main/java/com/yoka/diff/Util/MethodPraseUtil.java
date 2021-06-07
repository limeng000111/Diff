package com.yoka.diff.Util;

import cn.hutool.crypto.SecureUtil;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yoka.common.errorcode.BizCode;
import com.yoka.common.utils.exception.BizException;
import com.yoka.diff.Entity.MethodInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author:jack
 * @date 2021/6/7 10:33
 * @des:解析获取类的方法
 */


@Slf4j
public class MethodPraseUtil {
    /**
     * javaparse工具核心方法，主要通过这个类遍历class方法，取出所有方法，然后去对比是否存在差异
     */
    private static class MethodVisitor extends VoidVisitorAdapter<List<MethodInfoResult>>{
        @Override
        public void visit(MethodDeclaration declaration,List<MethodInfoResult> list){
            //计算方法体的hash值
            String md5 = SecureUtil.md5(declaration.toString());
            StringBuilder params = new StringBuilder();
            List<Parameter> parameters = declaration.getParameters();
            if (!CollectionUtils.isEmpty(parameters)){
                for (int i = 0;i < parameters.size();i++){
                    String param = parameters.get(i).getType().toString();
                    params.append(param.replace(" ",""));
                    if (i != parameters.size()-1){
                        params.append("&");
                    }
                }
            }
            MethodInfoResult result = MethodInfoResult.builder()
                    .md5(md5)
                    .methodName(declaration.getName())
                    .parameters(params.toString())
                    .build();
            list.add(result);
            super.visit(declaration,list);
        }
    }


    public static List<MethodInfoResult> parseMethods(String classFile){
        List<MethodInfoResult> list = new ArrayList<>();
        try (FileInputStream in = new FileInputStream(classFile)){
            JavaParser javaParser = new JavaParser();
            javaParser.parse(in).getResult().orElseThrow(() -> new BizException(BizCode.PARSE_JAVA_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
