package com.tanglx.wechat.framework.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis
 *
 * @Author tanglingxiao
 * @Date 2022-07-05
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key：关键字
     * @param expireTime：过期时间，单位秒（例如expireTime=30，为30秒） 填0将不设置
     * @param value：值
     */
    public void set(String key, int expireTime, Object value) {
        redisTemplate.opsForValue().set(key, value);
        if (expireTime > 0) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置缓存,没有过期时间
     *
     * @param key：关键字
     * @param value：值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key：关键字
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取缓存值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获得缓存中的数据并重置其过期时间.
     *
     * @param key：关键字
     */
    public Object getAndTouch(String key, int expireTime) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys：关键字数组
     */
    public void removeKeys(List<String> keys) {
        keys.forEach(k -> delete(k));
    }

    /**
     * 设置分布式锁
     *
     * @param key        唯一认证key，唯一性，推荐值：前缀+相应ID
     * @param value      识别是否是自己加的锁的标识，唯一性，推荐值：UUID
     * @param expireTime 过期时间，推荐 30 ，即30秒
     * @return 是否加锁成功
     */
    public Boolean getLock(String key, String value, int expireTime) {
        Object object = get(key);
        if (object != null) {
            return Boolean.FALSE;
        }
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE;
    }

    /**
     * 释放分布式锁
     *
     * @param key   锁
     * @param value 请求标识
     * @return 是否释放成功
     */
    public Boolean releaseLock(String key, String value) {
        Object object = get(key);
        if (object == null) {
            return Boolean.FALSE;
        }
        String redisValue = (String) object;
        if (!value.equals(redisValue)) {
            return Boolean.FALSE;
        }
        delete(key);
        return Boolean.TRUE;
    }
}
