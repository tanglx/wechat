package com.tanglx.wechat.service.wechat;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.tanglx.wechat.common.constant.BaseErrors;
import com.tanglx.wechat.common.constant.Constants;
import com.tanglx.wechat.common.exception.BaseException;
import com.tanglx.wechat.common.util.OkHttpUtils;
import com.tanglx.wechat.common.util.StringUtil;
import com.tanglx.wechat.common.vo.wechat.AccessTokenVo;
import com.tanglx.wechat.config.WechatConfig;
import com.tanglx.wechat.framework.redis.RedisService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-20
 */
@Log
@Service
public class WechatAccessTokenService {
    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private RedisService redisService;

    /**
     * 获取token
     *
     * @return
     */
    public String getAccessToken() {
        Object accessToken = redisService.get(Constants.WECHAT_ACCESS_TOKEN);
        if (StringUtil.isBlank(accessToken)) {
            accessToken = getWechatAccessToken();
        }
        if (StringUtil.isBlank(accessToken)) {
            throw new BaseException(BaseErrors.EX_OTHER_CODE);
        }
        return accessToken.toString();
    }

    /**
     * 请求token放redis
     */
    private String getWechatAccessToken() {
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", "client_credential");
        params.put("appid", wechatConfig.getAppid());
        params.put("secret", wechatConfig.getSecret());
        String token = OkHttpUtils.builder()
                .url("https://api.weixin.qq.com/cgi-bin/token")
                .addParam(params)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .get()
                .sync();
        log.info(token);
        AccessTokenVo accessTokenVo = JSON.parseObject(token, AccessTokenVo.class);
        if (accessTokenVo != null && StringUtil.isNotBlank(accessTokenVo.getAccess_token())) {
            redisService.set(Constants.WECHAT_ACCESS_TOKEN, accessTokenVo.getExpires_in() - 300, accessTokenVo.getAccess_token());
            return accessTokenVo.getAccess_token();
        }
        return null;
    }
}
