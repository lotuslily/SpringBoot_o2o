package com.cincc.o2o.cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * @author li
 * Date: 2018/06/04
 */

/**
 * 强指定redis的JedisPool接口构造函数，这样才能在centos成功创建jedispool
 */
public class JedisPoolWriper {
	/**
	 * Redis连接池对象
	 */
	private JedisPool jedisPool;

	public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host, final int port) {
		try {
			jedisPool = new JedisPool(poolConfig, host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Redis连接池对象
	 * @return
	 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	/**
	 * 注入Redis连接池对象
	 * @param jedisPool
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
