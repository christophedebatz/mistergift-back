package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.cache.redis.RedisCacheService;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 */
@Configuration
public class DataRedisConfiguration {

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient() {
        return RedisClient.create(clientResources(), "redis://localhost:6379");
    }

    @Bean
    public RedisCacheService redisCacheService() {
        return new RedisCacheService(redisClient());
    }

}
