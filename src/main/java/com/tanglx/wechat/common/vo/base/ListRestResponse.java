package com.tanglx.wechat.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@NoArgsConstructor
@Getter
@Setter
@ApiModel
public class ListRestResponse<T> extends BaseResponse {

    @ApiModelProperty("结果集")
    List<T> data;
    @ApiModelProperty("总记录数")
    int count = 0;

    public ListRestResponse(List<T> result, Long count) {
        this.setData(result);
        this.setCount(count.intValue());
    }

    public ListRestResponse(List<T> result, int count) {
        this.setData(result);
        this.setCount(count);
    }

    public ListRestResponse(List<T> result) {
        this.setData(result);
        this.setCount(result.size());
    }
}
