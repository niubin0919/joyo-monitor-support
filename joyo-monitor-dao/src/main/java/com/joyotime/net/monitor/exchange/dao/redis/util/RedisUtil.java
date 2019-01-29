/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.redis.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.joyotime.net.monitor.exchange.dao.redis.base.RedisConfig;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * redis工具类
 * @author nbin
 * @version $Id: RedisUtil.java, v 0.1 2019年1月15日 下午7:25:04 nbin Exp $
 */
@Slf4j
public class RedisUtil {
    
    private static int               redisModel = -1;
    private static JedisPool         jedisSinglePool;
    private static JedisSentinelPool jedisClusterPool;

    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setJmxEnabled(false);//关闭JMX管理功能
        config.setMaxTotal(RedisConfig.getPoolMaxActive());
        config.setMaxIdle(RedisConfig.getPoolMaxIdle());
        config.setMinIdle(RedisConfig.getPoolMinIdle());
        config.setMaxWaitMillis(RedisConfig.getPoolMaxWait());
        config.setTestOnBorrow(RedisConfig.getPoolTestOnBorrow());
        config.setTestOnReturn(RedisConfig.getPoolTestOnReturn());
        config.setTestWhileIdle(RedisConfig.getPoolTestWhileIdle());
        config.setTimeBetweenEvictionRunsMillis(RedisConfig.getPoolTimeBetweenEvictionRunsMillis());
        config.setNumTestsPerEvictionRun(RedisConfig.getPoolNumTestsPerEvictionRun());
        config.setMinEvictableIdleTimeMillis(RedisConfig.getPoolMinEvictableIdleTimeMillis());
        config.setSoftMinEvictableIdleTimeMillis(RedisConfig.getPoolSoftMinEvictableIdleTimeMillis());
        redisModel = RedisConfig.getModel();
        if (redisModel == 1) {
            String pwd = RedisConfig.getSinglePassword();
            if (pwd == null) {
                jedisSinglePool = new JedisPool(config, RedisConfig.getSingleHost(), RedisConfig.getSinglePost(), RedisConfig.getTimeout());
            } else {
                jedisSinglePool = new JedisPool(config, RedisConfig.getSingleHost(), RedisConfig.getSinglePost(), RedisConfig.getTimeout(), pwd);
            }
        } else {
            String pwd = RedisConfig.getClusterPassword();
            if (pwd == null) {
                jedisClusterPool = new JedisSentinelPool(RedisConfig.getClusterMasterName(), RedisConfig.getClusterSentinelsIps(), config);
            } else {
                jedisClusterPool = new JedisSentinelPool(RedisConfig.getClusterMasterName(), RedisConfig.getClusterSentinelsIps(), config, pwd);
            }
            HostAndPort currentHostMaster = jedisClusterPool.getCurrentHostMaster();
            log.info("当前主节点信息:" + currentHostMaster.getHost() + "--" + currentHostMaster.getPort());
            log.info("redis连接池初始化成功:{}", jedisClusterPool);
        }
    }

    public static JedisSentinelPool getJedisClusterPool() {
        HostAndPort currentHostMaster = jedisClusterPool.getCurrentHostMaster();
        log.info("当前主节点信息:" + currentHostMaster.getHost() + "--" + currentHostMaster.getPort());// 获取主节点的信息
        return jedisClusterPool;
    }

    public static JedisPool getJedisSinglePool() {
        return jedisSinglePool;
    }

    /**
     * 获取一个jedis实例
     * 
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            if (redisModel == 1) {
                jedis = jedisSinglePool.getResource();
            } else {
                jedis = jedisClusterPool.getResource();
            }
            if (jedis != null) {
                jedis.select(RedisConfig.getDbIndex());
            }
        } catch (Exception e) {
            log.warn("获取jedis对象失败：\n" + e.getMessage(), e);
            if (redisModel != 1) {
                try {
                    Thread.sleep(3000L); 
                } catch (Exception e2) {
                }
            }
            RedisUtil.returnJedis(jedis);
        }
        return jedis;
    }
    
    /**
     * 获取jedis对象，先检测实例是否正常连接
     * 如果没有正常连接，获取一个新的jedis实例
     * @param jedis
     * @return
     */
    public static Jedis getJedis(Jedis jedis) {
        if (jedis == null) {
            return getJedis();
        }
        if (redisModel == 1) {
            return jedis;
        }
        // 集群模式检查
        if (jedis.isConnected()) {
            try {
                jedis.set("jedis-connected-test", "1");
                return jedis;
            } catch (Exception e) {
                RedisUtil.returnJedis(jedis);
                log.warn("jedis执行set命令失败，重新获取连接：" + e.getMessage());
                try {
                    Thread.sleep(3000L);
                } catch (Exception e2) {
                }
                return getJedis();
            }
        } else {
            RedisUtil.returnJedis(jedis);
            log.warn("jedis连接断开，重新获取连接..");
            try {
                Thread.sleep(3000L);
            } catch (Exception e2) {
            }
            return getJedis();
        }
    }

    /**
     * 回收jedis实例
     * 
     * @param jedis
     */
    public static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error("", e);
                log.warn("回收jedis实例失败：" + e.getMessage());
            }
        }
    }
    
    /**
     * del
     */
    public static long hdel(String key, String fieid) {
        Jedis jedis = null;
        long s = -1;
        try {
            jedis = getJedis();
            s = jedis.hdel(key, fieid);
        } finally {
            RedisUtil.returnJedis(jedis);
        }
        return s;
    }
    
    /**
     * del
     */
    public static long hdel(Jedis jedis, String key, String fieid) {
        long s = -1;
        try {
            jedis = getJedis(jedis);
            s = jedis.hdel(key, fieid);
        } catch (Exception e) {
            log.error(null, e);
        }
        return s;
    }
    
    /**
     * hset
     * @param key
     * @param fieid
     * @param value
     * @param index
     * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
     */
    public static long hset(String key, String fieid, String value, int index) {
        Jedis jedis = null;
        long s;
        try {
            jedis = getJedis();
            jedis.select(index);
            s = jedis.hset(key, fieid, value);
        } finally {
            RedisUtil.returnJedis(jedis);
        }
        return s;
    }
    /**
     * 获取
     * @param key
     * @return
     */
    public static Map<String, String> hgetAll(String key) {
        Map<String, String> map = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            log.debug(null, e);
        } finally {
            returnJedis(jedis);
        }
        return map;
    }

    /**
     * exe redis
     * 
     * @param scrpit
     * @param keys
     * @param args
     * @return
     * @throws Exception
     */
    public static Object ExeRedisScrpit(String scrpit, List<String> keys, List<String> args){
        Jedis jedis = null;
        Object val = null;
        try {
            jedis = RedisUtil.getJedis();
            val = jedis.eval(scrpit, keys, args);
        } catch (Exception e) {
            if (args.size() > 0) {
                for (String string : args) {
                    scrpit += "\n" + string;
                }
            }
            log.error("Redis脚本执行失败：\n" + scrpit + "\n" + e.getMessage(), e);
        } finally {
            RedisUtil.returnJedis(jedis);
        }
        return val;
    }
    
    
    /**
     * 获取追加数据到缓冲脚本 ARGV[1] = 需要追加的数据
     * 
     * @param cacheKeyName 数据缓冲区Key
     * @param dataPrefix   数据前缀
     * @param queueKeyName 消费队列（list）Key
     * @param bufferSize   缓冲区最大长度，满足长度后将数据移入消费队列
     * @return
     */
    public static String GetAppendScript(String cacheKeyName, String dataPrefix, String queueKeyName, int bufferSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("local bufKeyName = '" + cacheKeyName + "'; \n");
        sb.append("local bufInitValue = '" + dataPrefix + "'; \n");
        sb.append("local buflenKeyName = '" + cacheKeyName + "_BufSize" + "'; \n");
        sb.append("local cqueueKeyName = '" + queueKeyName + "'; \n");
        sb.append("local haveKey = redis.call('exists', bufKeyName); \n");
        sb.append("local buflen = tonumber(redis.call('get', buflenKeyName)); \n");
        sb.append("if haveKey > 0 then \n");
        sb.append("  if buflen > 0 then \n");
        sb.append("    ARGV[1] = ','..ARGV[1]; \n");
        sb.append("  end \n");
        sb.append("  redis.call('append', bufKeyName, ARGV[1]); \n");
        sb.append("  buflen = redis.call('incr', buflenKeyName); \n");
        sb.append("else \n");
        sb.append("  redis.call('set', bufKeyName, bufInitValue..ARGV[1]); \n");
        sb.append("  redis.call('set', buflenKeyName, 1); \n");
        sb.append("  buflen = 1; \n");
        sb.append("end \n");
        sb.append("if buflen >= " + bufferSize + " then \n");
        sb.append("  local val = redis.call('get', bufKeyName); \n");
        sb.append("  redis.call('rpush', cqueueKeyName, val); \n");
        sb.append("  redis.call('set', bufKeyName, bufInitValue); \n");
        sb.append("  redis.call('set', buflenKeyName, 0); \n");
        sb.append("  return 2; \n");
        sb.append("end \n");

        sb.append("return 1; \n");

        return sb.toString();
    }

    /**
     * 获取消费队列脚本（非阻塞） 脚本返回2个参数，参数1：实际数据，参数2：数据临时区字段名 ARGV[1] = 一个GUID字符串
     * 
     * @param consumerQueueName 消费队列（list）key
     * @param consumerQueueNameTemp 取出数据的临时(map)key
     * @return
     */
    public static String GetConsumerScript(String consumerQueueName, String consumerQueueNameTemp) {
        StringBuilder sb = new StringBuilder();
        sb.append("local cqueueKey = '" + consumerQueueName + "'; \n");
        sb.append("local cqueueKeyTemp = '" + consumerQueueNameTemp + "'; \n");
        sb.append("if redis.call('exists', cqueueKey) == 0 then \n");
        sb.append("   return nil; \n");
        sb.append("end \n");
        sb.append("local flen = redis.call('hlen',cqueueKeyTemp); \n");
        sb.append("local fieldName = 'tm_'..ARGV[1]..'_'..tostring(flen); \n");
        sb.append("local dsql = redis.call('lpop', cqueueKey); \n");
        sb.append("if dsql ~= nil then \n");
        sb.append("   redis.call('hset',cqueueKeyTemp,fieldName,dsql); \n");
        sb.append("   return fieldName..'|'..dsql; \n");
        sb.append("end \n");
        sb.append("return nil; \n");
        return sb.toString();
    }
    
   
}
