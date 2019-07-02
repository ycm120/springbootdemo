package com.ycm.redis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 默认情况下，缓存的key就是方法的参数，缓存的value就是方法的返回值
		// 当有多个参数时，默认就使用多个参数来做key，如果只需要其中某一个参数做key，
		// 则可以在@Cacheable注解中，通过key属性来指定key，如上代码就表示只使用id作为缓存的key，
		// 如果对key有复杂的要求，可以自定义keyGenerator。当然，Spring Cache中提供了root对象，
		// 可以在不定义keyGenerator的情况下实现一些复杂的效果
		// @Cacheable
		// 这个注解一般加在查询方法上，表示将一个方法的返回值缓存起来，
		String a = getData("key1", "getK2");
		System.out.println(a);
		// @CachePut
		// 这个注解一般加在更新方法上，当数据库中的数据更新后，缓存中的数据也要跟着更新，
		// 使用该注解，可以将方法的返回值自动更新到已经存在的key上
		String b = updateData("key1", "updateK2");
		System.out.println(b);
		// @CacheEvict
		// 这个注解一般加在删除方法上，当数据库中的数据删除后，相关的缓存数据也要自动清除，该注解在使用的时候也可以配置按照某种条件删除（condition属性）或者或者配置清除所有缓存（allEntries属性）
		deleteData("key1", "deleteK2");
	}

	@Cacheable(key = "#k88")
//	@Cacheable(key = "#root.args[0]")
	private String getData(String k1, String k2) {
		return k1 + k2;
	}
	
	@CachePut(key = "#k88")
	private String updateData(String k1, String k2) {
		return k1 + k2;
	}
	
	@CacheEvict(key = "#k88", cacheNames="c1")
	private void deleteData(String string, String string2) {
		System.out.println("delete data cache also delete");
	}


}
