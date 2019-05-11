package com.walter.base.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	
	@Value("${custom.security.login.jwt.alivedMinutes}")
	private int JWT_ALIVED_MINUTES;
	
	@Value("${custom.security.login.captcha.alivedSeconds}")
	private int CAPTCHA_ALIVED_SECONDS;
	
	/**缓存SecurityContext*/
	public final static String USERNAME_SECURITY_CONTEXT_CACHE = "username-security-context-cache";
	
	/**缓存登录时的图片验证码*/
	public final static String LOGIN_CAPTCHA_CACHE = "login-captcha-cache";
	
	// 自定义key生成器
	@Bean
	public KeyGenerator keyGenerator() {
		return (o, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getClass().getName()).append(".") // 类名
			  .append(method.getName()); // 方法名
			for (Object param : params) {
				sb.append(".").append(param.toString()); // 参数名
			}
			return sb.toString();
		};
	}

	// 配置缓存管理器
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(JWT_ALIVED_MINUTES))
				// 设置key的序列化方式
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				// 设置value的序列化方式
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
				// 不缓存null值
				.disableCachingNullValues();
		
		// 设置一个初始化的缓存空间set集合
		Set<String> cacheNames = new HashSet<>();
		cacheNames.add(USERNAME_SECURITY_CONTEXT_CACHE);
		cacheNames.add(LOGIN_CAPTCHA_CACHE);

        // 对每个缓存空间应用不同的配置
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // 通过Duration可以自己实现以什么时间为单位
	    configMap.put(USERNAME_SECURITY_CONTEXT_CACHE, config.entryTtl(Duration.ofMinutes(JWT_ALIVED_MINUTES)));
	    configMap.put(LOGIN_CAPTCHA_CACHE, config.entryTtl(Duration.ofMinutes(CAPTCHA_ALIVED_SECONDS)));
        
		RedisCacheManager redisCacheManager = RedisCacheManager
				.builder(connectionFactory)
				.cacheDefaults(config)
				.initialCacheNames(cacheNames)// 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
				.withInitialCacheConfigurations(configMap)
				.transactionAware()
				.build();

		return redisCacheManager;
	}

	// key键序列化方式
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	// value值序列化方式
	private RedisSerializer<Object> valueSerializer() {
		return new JdkSerializationRedisSerializer();
	}
}
