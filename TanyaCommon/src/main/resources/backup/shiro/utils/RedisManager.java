/**
 * Title: RedisManager.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.shiro
 * @author Sharp
 * @date 2019-02-05 21:28:06
 */
package com.srct.service.tanya.common.config.shiro.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import com.srct.service.utils.BeanUtil;

/**
 * @author Sharp
 *
 */
@Configuration
public class RedisManager {

    private static Logger logger = LoggerFactory.getLogger(RedisManager.class);

    private RedisTemplate<String, Object> redisTemplate;

    private RedisTemplate<String, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>)BeanUtil.getBean("shiroRedisTemplate");
        }
        return redisTemplate;
    }

    @Bean(name = "shiroRedisTemplate")
    public RedisTemplate<String, byte[]> shiroRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new ShiroSerializer());
        redisTemplate.setHashValueSerializer(new ShiroSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    // =============================common============================
    /**
     * 指定缓存失效时间
     * 
     * @param key
     *            键
     * @param time
     *            时间(秒)
     */
    public void expire(String key, long time) {
        getRedisTemplate().expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * 
     * @param key
     *            键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return getRedisTemplate().hasKey(key);
    }

    /**
     * 删除缓存
     * 
     * @param key
     *            可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                getRedisTemplate().delete(key[0]);
            } else {
                getRedisTemplate().delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除key
     * 
     * @param keys
     */
    public void del(Collection keys) {
        getRedisTemplate().delete(keys);
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * 
     * @param key
     *            键
     * @return 值
     */
    public Object get(String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * 
     * @param key
     *            键
     * @param value
     *            值
     */
    public void set(String key, Object value) {
        getRedisTemplate().opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            getRedisTemplate().opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 使用scan命令 查询某些前缀的key
     * 
     * @param key
     * @return
     */
    public Set<String> scan(String key) {
        Set<String> execute = this.getRedisTemplate().execute(new RedisCallback<Set<String>>() {

            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

                Set<String> binaryKeys = new HashSet<>();

                Cursor<byte[]> cursor =
                    connection.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            }
        });
        return execute;
    }

    /**
     * 使用scan命令 查询某些前缀的key 有多少个 用来获取当前session数量,也就是在线用户
     * 
     * @param key
     * @return
     */
    public Long scanSize(String key) {
        long dbSize = this.getRedisTemplate().execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long count = 0L;
                Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(key).count(1000).build());
                while (cursor.hasNext()) {
                    cursor.next();
                    count++;
                }
                return count;
            }
        });
        return dbSize;
    }
}
