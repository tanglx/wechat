package com.tanglx.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String appid;

    private String secret;

    private String token;

    private String encodingAESKey;
}
