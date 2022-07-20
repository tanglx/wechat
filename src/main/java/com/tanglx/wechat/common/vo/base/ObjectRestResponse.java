package com.tanglx.wechat.common.vo.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@Getter
@Setter
@ApiModel
public class ObjectRestResponse<T> extends BaseResponse {

    T data;

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public ObjectRestResponse status(int status) {
        this.setStatus(status);
        return this;
    }

    public ObjectRestResponse(T data) {
        this.setData(data);
    }

    public ObjectRestResponse() {
    }
}
