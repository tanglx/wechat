package com.tanglx.wechat.api;

import com.tanglx.wechat.common.entity.User;
import com.tanglx.wechat.service.UserService;
import com.tanglx.wechat.service.wechat.WechatAccessTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-19
 */
@RestController
@RequestMapping("/button")
@Api(tags = "11", produces = "application/json")
public class UserApi {
    @Resource
    private UserService userService;
    @Resource
    private WechatAccessTokenService wechatAccessTokenService;

    @ApiOperation(value = "11")
    @GetMapping
    public String get() {
        return wechatAccessTokenService.getAccessToken();
    }

    @ApiOperation(value = "22")
    @PostMapping
    public User add(@RequestBody User request) {
        return userService.add(request);
    }
}
