/**
 * 
 */
package com.ch.base.dao;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author mozhiqiang
 *
 */
public class AbstractBaseRedisDao<K,V> {
	
	@Resource
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * 设置redisTemplate
	 * @param redisTemplate the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * 获取 RedisSerializer
	 * <br>------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

}
