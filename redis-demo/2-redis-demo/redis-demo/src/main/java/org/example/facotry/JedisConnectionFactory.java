package org.example.facotry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionFactory {

    private static final JedisPool jedisPool;

    static {
        // 配置连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);   // 最大空闲连接
        poolConfig.setMinIdle(0);   // 最小空闲连接
        poolConfig.setMaxWaitMillis(1000);  // 等待时长
        // 创建连接池对象
        jedisPool = new JedisPool(poolConfig,
                "8.130.73.113",
                6379,
                1000,
                "1234");
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
