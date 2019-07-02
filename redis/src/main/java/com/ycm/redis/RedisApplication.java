package com.ycm.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class RedisApplication implements ApplicationRunner {

	@Autowired
	RedisTemplate redisTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	/**
	 * Redis 中的数据操作，大体上来说，可以分为两种：针对 key 的操作，相关的方法就在 RedisTemplate 中
	   *  针对具体数据类型的操作，相关的方法需要首先获取对应的数据类型，获取相应数据类型的操作方法是 opsForXXX
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set("k1", "v1");
		Object object1 = opsForValue.get("k1");
		System.out.println("ds" + object1);
		//k1 前面的字符是由于使用了 RedisTemplate 导致的，RedisTemplate 对 key 进行序列化之后的结果。
		//RedisTemplate 中，key 默认的序列化方案是 JdkSerializationRedisSerializer 。
		//而在 StringRedisTemplate 中，key 默认的序列化方案是 StringRedisSerializer ，因此，如果使用 StringRedisTemplate ，默认情况下 key 前面不会有前缀。
		// 不过开发者也可以自行修改 RedisTemplate 中的序列化方案，如下:
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		ValueOperations opsForValue2 = redisTemplate.opsForValue();
		opsForValue2.set("k2", "v2");
		Object object2 = opsForValue2.get("k2");
		System.out.println("ds" + object2);
	}

}
