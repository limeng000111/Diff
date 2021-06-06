package com.yoka.diff.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.testng.annotations.Test;

/**
 * 代码管理工具枚举
 */
@Getter
@AllArgsConstructor
public enum CodeManageTypeEnum {

    GIT(0,"git"),

    SVN(1,"svn");

    private Integer code;

    private String value;

    /**
     * 通过code获取值
     * @param code
     * @return
     */
    public static String getValueByCode(Integer code){
        CodeManageTypeEnum[] values = CodeManageTypeEnum.values();
//        System.out.println("values的值为："+values.toString());
        for (CodeManageTypeEnum type : values){
            if (type.code == code){
                return type.value;
            }
        }
        return null;
    }

    /**
     * 通过value获取code
     * @param value
     * @return
     */
    public static Integer getCodeByValue(String value){
        CodeManageTypeEnum[] values = CodeManageTypeEnum.values();
        for (CodeManageTypeEnum type : values){
            if (type.value.equalsIgnoreCase(value)){
                return type.code;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getValueByCode(1);
    }
}
