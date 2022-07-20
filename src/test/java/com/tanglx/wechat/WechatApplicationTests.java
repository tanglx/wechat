package com.tanglx.wechat;

import com.tanglx.wechat.service.wechat.WechatAccessTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WechatApplicationTests {

    @Resource
    WechatAccessTokenService wechatAccessTokenService;

    @Test
    void contextLoads() {
        wechatAccessTokenService.getAccessToken();
    }

}
