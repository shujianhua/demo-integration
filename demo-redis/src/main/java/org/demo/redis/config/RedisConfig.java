package org.demo.redis.config;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

	/**
	 * 定义 RedisTemplate 配置, 替换 spring boot 默认装载的 redisTemplate （序列化器问题）
	 * @param redisConnectionFactory
	 * @return redisTemplateDemo
	 */
	@Bean
	public RedisTemplate<String, Serializable> redisTemplateDemo(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		// 使用 Jackson2JsonRedisSerializer 替换默认序列化
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 字符串序列化器
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// 普通Key设置为字符串序列化器
		template.setKeySerializer(stringRedisSerializer);
		// Hash结构的key设置为字符串序列化器
		template.setHashKeySerializer(stringRedisSerializer);
		// 普通值和hash的值都设置为jackson序列化器
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}