package com.tanglx.wechat.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-21
 */
@RestController
@RequestMapping("/wechat")
@Api(tags = "Wechat", produces = "application/json")
public class WechatController {
    @Resource
    private WxMpService wxMpService;

    @PostMapping("/signature/check")
    @ApiOperation("check")
    public String check(String timestamp, String nonce, String signature, String echostr) {
        boolean flag = wxMpService.checkSignature(timestamp, nonce, signature);
        if (flag) {
            return echostr;
        }
        return null;
    }
}
