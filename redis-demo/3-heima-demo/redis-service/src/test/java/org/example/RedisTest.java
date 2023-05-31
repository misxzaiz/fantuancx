package org.example;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.dto.RedisDate;
import org.example.entity.User;
import org.example.service.IUserService;
import org.example.utils.CacheClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IUserService userService;

    @Resource
    private CacheClient client;

    @Test
    public void testSetnxLock(){
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("lock:" + "key", "1", 10, TimeUnit.MINUTES);
        System.out.println(aBoolean);
    }

    @Test
    public void testDelLock(){
        stringRedisTemplate.delete("lock:" + "key");
    }

    @Test
    public void testQueryById(){
        System.out.println(query(1L));
    }

    @Test
    public void testSetExpireSeconds(){
//        setExpireSeconds(1L,1L);
        System.out.println(this.query2(1L));
    }

    private static final ExecutorService CACHE_REBUILE_EXECUTOR = Executors.newFixedThreadPool(10);

    public User query2(Long id){
        // 1. 从 redis 查询缓存
        String userJson = stringRedisTemplate.opsForValue().get("fantuan:user:"+id);
        // 2. 判断是否存在
        if (StrUtil.isBlank(userJson)) {
            return null;
        }
        // 4. 命中，需要先把 json 反序列化为对象
        RedisDate redisDate = JSONUtil.toBean(userJson, RedisDate.class);
        // User user = (User) redisDate.getData();
        User user = JSONUtil.toBean((JSONObject) redisDate.getData(), User.class);
        LocalDateTime expireTime = redisDate.getExpireTime();
        // 5. 判断是否过期
        System.out.println("expireTime:"+expireTime);
        System.out.println("LocalDateTime.now():"+LocalDateTime.now());
        if (expireTime.isAfter(LocalDateTime.now())) {
            // 5.1 未过期，返回信息
            return user;
        }
        System.out.println("已过期！");
        // 5.2 已过期，缓存重建
        // 6. 缓存重建
        // 6.1 获取互斥锁
        try {
            if(Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent("fantuan:lock:" + id, "1", 10, TimeUnit.MINUTES))){
                CACHE_REBUILE_EXECUTOR.submit(() -> {
                    // 缓存重建
                    this.setExpireSeconds(id,2L);
                });
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            // 释放锁
            stringRedisTemplate.delete("fantuan:lock:" + id);
        }

        // 返回过期数据
        return user;
    }





    public void setExpireSeconds(Long id,Long expireSeconds){
        System.out.println("写新数据到缓存");
        // 1. 查询数据库
        // User user = userService.getById(id);
        User user = new User();
        user.setId(id);
        user.setNickName("小2肖");
        // 2. 封装逻辑过期时间
        RedisDate redisDate = new RedisDate();
        redisDate.setData(user);
        redisDate.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        // 3. 写入 Redis
        stringRedisTemplate.opsForValue().set("fantuan:user:"+id,JSONUtil.toJsonStr(redisDate));
    }

    public User query(Long id){
        String key = "redis:user:"+id;
        User user = null;
        // 1. 从 redis 中查询用户数据
        String userJson = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(StrUtil.isNotBlank(userJson)){
            // 2.1 存在，直接返回
            System.out.println(userJson);
            return JSONUtil.toBean(userJson,User.class);
        }
        // 3. 不存在，判断命中是否为空值
//        if (userJson == null) {
//            // 3.1 返回错误信息
//            return null;
//        }
        try {
            // 4. 实现缓存重建
            // 4.1 获取互斥锁
            if(!Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent("lock:" + key, "1", 10, TimeUnit.MINUTES))){
                // 1. 获取锁失败，休眠并重试
                Thread.sleep(50);
                // 2. 递归重试
                return query(id);
            }
            // 4.2 根据id查询用户
            // User userQW = userService.getById(id);
            // user = userQW;
            User userQW = new User();
            userQW.setId(id);
            userQW.setNickName("小肖");
            user = userQW;
            System.out.println(userQW);
            System.out.println(user);
            // 5. 不存在
            if (userQW == null) {
                // 5.1 设置空值，避免缓存穿透
                stringRedisTemplate.opsForValue().set(key,"",2,TimeUnit.MINUTES);
                // 5.2 返回错误信息
                return null;
            }
            // 6. 存在，写入 redis
            stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(user),30,TimeUnit.MINUTES);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        } finally {
            // 7. 释放互斥锁
            stringRedisTemplate.delete("lock:" + "key");
        }
        // 8. 返回
        return user;
    }

    @Test
    public void test(){
        client.set("fantuan:utils:user:1",getById(1L),100L,TimeUnit.MINUTES);
    }

    public User getById(Long id){
        User user = new User();
        user.setId(id);
        user.setNickName("小肖");
        return user;
    }

    @Test
    public void testCacheClient(){
        User user = client.queryWithPassThrough("fantuan:utils:user",1L,
                User.class, 1L, TimeUnit.MINUTES,
                id2 -> getById(id2));
        System.out.println(user);
//        User user = client.queryWithPassThrough("fantuan:user",1L,
//                User.class, 1L, TimeUnit.MINUTES,
//                this::getById);
    }


    private static final long BEGIN_TIMESTAMP = 1640995200L;
    private static final int COUNT_BITS = 32;

    /**
     * 全局唯一 ID 生成器
     * @param keyPrefix
     * @return
     */
    public long nextId(String keyPrefix){
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;
        // 2. 生成序列号
        // 获取当前日期
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        Long increment = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + yyyyMMdd);
        // 3. 拼接并返回
         return timestamp << COUNT_BITS | increment;
    }

    @Test
    public void testNextId(){
        for (int i = 0; i < 100; i++) {
            System.out.println(this.nextId("user"));
        }
    }

}
