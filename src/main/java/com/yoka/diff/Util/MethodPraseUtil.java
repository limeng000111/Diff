package com.yoka.diff.Util;

import cn.hutool.crypto.SecureUtil;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yoka.common.errorcode.BizCode;
import com.yoka.common.utils.exception.BizException;
import com.yoka.diff.Entity.MethodInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(MethodPraseUtil.class);


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

    /**
     * 解析类，获取类的所有方法
     * @param classFile
     * @return
     */
    public static List<MethodInfoResult> parseMethods(String classFile){
        List<MethodInfoResult> list = new ArrayList<>();
        try (FileInputStream in = new FileInputStream(classFile)){
//            JavaParser javaParser = new JavaParser();
            CompilationUnit parse = JavaParser.parse(in);
            //排除接口类（jacoco不会统计）
            List<?> types = parse.getTypes();
            boolean isInterface = types.stream().filter(t -> t instanceof ClassOrInterfaceDeclaration).anyMatch(t -> ((ClassOrInterfaceDeclaration) t).isInterface());
            if (isInterface){
                return list;
            }
            parse.accept(new MethodVisitor(),list);
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new BizException(BizCode.PARSE_JAVA_FILE);
        }

        return null;
    }
}
