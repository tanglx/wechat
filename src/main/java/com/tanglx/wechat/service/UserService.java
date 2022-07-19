package com.tanglx.wechat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanglx.wechat.common.entity.User;
import com.tanglx.wechat.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * 用户信息
 *
 * @Author tanglingxiao
 * @Date 2022-06-09
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User add(User user) {
        save(user);
        return user;
    }
}
