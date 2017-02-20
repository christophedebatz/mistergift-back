package com.gvstave.mistergift.data.service.cache.redis;

import com.gvstave.mistergift.data.service.cache.CacheService;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Redis cache service implementation.
 */
@Service
public class RedisCacheService implements CacheService {

    /** The redis client factory bean. */
    private RedisClient redis;

    /** The Redis commander. */
    private Optional<RedisCommands<String, Object>> commander;

    /**
     * Constructor with @Inject of redis.
     *
     * @param redis The Redis client.
     */
    public RedisCacheService(RedisClient redis) {
        this.redis = redis;
        this.commander = getCommander();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getKeys() {
        if (commander.isPresent()) {
            return commander.get().scan().getKeys().stream()
                .map(str -> str.replace("\u0000", ""))
                .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String... keys) {
        return commander.isPresent() && commander.get().del(keys) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String name, Object value, Long ttl) {
        if (commander.isPresent()) {
            commander.get().set(name, value);

            if (ttl != null) {
                commander.get().expire(name, ttl);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String name, Object value) {
        set(name, value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(String name) {
        return get(name, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(String name, Object defaultValue) {
        Object value = null;

        if (commander.isPresent()) {
            value = commander.get().get(name);
        }

        return value != null ? value : defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getTTL(String key) {
        if (commander.isPresent()) {
            return commander.get().ttl(key);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String key) {
        return commander.isPresent() && commander.get().get(key) != null;

    }

    /**
     * Returns the redis commander.
     *
     * @return The commander.
     */
    private Optional<RedisCommands<String, Object>> getCommander() {
        return Optional.ofNullable(redis.connect(new SerializedObjectCodec()).sync());
    }

}
