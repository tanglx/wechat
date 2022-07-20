package com.tanglx.wechat.common.vo.wechat;

import lombok.Data;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-20
 */
@Data
public class AccessTokenVo {

    /**
     * 获取到的凭证
     */
    private String access_token;

    /**
     * 凭证有效时间，单位：秒
     */
    private int expires_in;
}
