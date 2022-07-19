package com.tanglx.wechat.config;

import javax.annotation.PostConstruct;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-06-09
 */
public class DruidConfig {

    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
