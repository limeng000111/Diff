package com.yoka.common.response;

import com.yoka.common.errorcode.BaseCode;
import com.yoka.common.errorcode.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author:jack
 * @date 2021/6/8 15:53
 * @des:todo
 */

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniqueApiResponse<T> extends ApiResponse<T> {

    private String uniqueData;

    public UniqueApiResponse<T> success(T data,String uniqueData){
        super.setCode(BaseCode.SUCCESS.getCode());
        super.setMsg(BaseCode.SUCCESS.getInfo());
        super.setData(data);
        this.uniqueData = uniqueData;
        return this;
    }
}
