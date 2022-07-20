package com.tanglx.wechat.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@Getter
@Setter
@ApiModel
public class BaseResponse {
    @ApiModelProperty("业务状态")
    private int status = HttpStatus.OK.value();
    @ApiModelProperty("提示信息")
    private String message;
    @ApiModelProperty("时间戳")
    private long timestamp = System.currentTimeMillis();

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }
}
