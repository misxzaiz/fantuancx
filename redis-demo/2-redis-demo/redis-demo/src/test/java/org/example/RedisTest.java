package org.example;

import org.example.facotry.JedisConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisTest {

    private Jedis jedis;

    /**
     * 建立连接
     */
    @BeforeEach
    void setUp() {
        // 1.建立连接
        // jedis = new Jedis("8.130.73.113", 6379);
         jedis = JedisConnectionFactory.getJedis(); // 使用连接池
        // 2.设置密码
        // jedis.auth("1234");
        // 3.选择库
        jedis.select(1);
    }

    /**
     * 释放资源
     */
    @AfterEach
    void tearDown() {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * String 测试
     */
    @Test
    void testString() {
        // 存入数据
        String result = jedis.set("name", "虎哥");
        System.out.println("result = " + result);
        // 获取数据
        String name = jedis.get("name");
        System.out.println("name = " + name);
    }

    /**
     * Hash 测试
     */
    @Test
    void testHash() {
        // 插入hash数据
        jedis.hset("user:1", "name", "Jack");
        jedis.hset("user:1", "age", "21");

        // 获取
        Map<String, String> map = jedis.hgetAll("user:1");
        System.out.println(map);
    }
}
