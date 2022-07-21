package com.tanglx.wechat.api;

import com.tanglx.wechat.service.wechat.WechatService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private WechatService wechatService;

    @GetMapping("/signature/check")
    //微信服务器根据小程序配置的token，结合时间戳timestamp和随机数nonce通过SHA1生成签名，发起get请求，检验token的正确性，
    //检验正确原样返回随机字符串echostr，失败返回空字符串
    public String check(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        return wechatService.getCheckSignature(signature, timestamp, nonce, echostr);
    }

}
