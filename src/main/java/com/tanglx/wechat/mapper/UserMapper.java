package com.tanglx.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanglx.wechat.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息 Mapper 接口
 *
 * @Author tanglingxiao
 * @Date 2022-06-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
