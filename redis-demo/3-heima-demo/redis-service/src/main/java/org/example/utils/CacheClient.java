package org.example.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.example.dto.RedisDate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class CacheClient {

    private final StringRedisTemplate stringRedisTemplate;

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key , JSONUtil.toJsonStr(value), time, unit);
    }

    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        RedisDate redisDate = new RedisDate();
        redisDate.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        redisDate.setData(value);
        stringRedisTemplate.opsForValue().set(key , JSONUtil.toJsonStr(redisDate));
    }

    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type,
                                          Long time, TimeUnit unit,
                                          Function<ID, R> dbFallback
    ){
        String key = keyPrefix + ":" + id;
        // 1. 从 redis 查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        System.out.println("json:"+json);
        // 2. 判断 json 是否存在
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        // 3. 判断是否为空值，缓存穿透
        if (json != null) {
            return null;
        }
        // 4. 查询数据库
        R r = dbFallback.apply(id);
        // 5. 判断是否为 null
        if (r == null) {
            // 将 null 写入 redis
            stringRedisTemplate.opsForValue().set(key,"",2L,TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        // 6. 存在，写入 redis
        this.set(key,id,time,unit);
        // 7. 返回
        return r;
    }
}
