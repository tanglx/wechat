package com.tanglx.wechat.api;

import com.tanglx.wechat.common.entity.User;
import com.tanglx.wechat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-19
 */
@RestController
@RequestMapping("/button")
@Api(tags = "按钮", produces = "application/json")
public class UserApi {
    @Resource
    private UserService userService;

    @ApiOperation(value = "按钮新增")
    @PostMapping
    public User add(@RequestBody User request) {
        return userService.add(request);
    }
}
