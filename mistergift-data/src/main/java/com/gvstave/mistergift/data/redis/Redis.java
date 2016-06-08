package com.gvstave.mistergift.data.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.springframework.stereotype.Service;

@Service
public class Redis {

    /** The redis client factory bean. */
    private RedisClient redis;

    /**
     * Default constructor.
     */
    public Redis() {
    }

    /**
     * Constructor with @Inject of redis.
     *
     * @param redis
     */
    public Redis(RedisClient redis) {
        this.redis = redis;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public RedisCommands<String, Object> getCommander() throws Exception {
        return redis.connect(new SerializedObjectCodec()).sync();
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, Object value) throws Exception {
        RedisCommands<String, Object> commander = getCommander();

        if (commander != null) {
            commander.set(name, value.toString());
        }
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Object get(String name) throws Exception {
        return get(name, null);
    }

    /**
     *
     * @param name
     * @param defaultValue
     * @return
     * @throws Exception
     */
    public Object get(String name, Object defaultValue) throws Exception {
        RedisCommands<String, Object> commander = getCommander();
        Object value = null;

        if (commander != null) {
            value = commander.get(name);
        }

        return value != null ? value : defaultValue;
    }

}
