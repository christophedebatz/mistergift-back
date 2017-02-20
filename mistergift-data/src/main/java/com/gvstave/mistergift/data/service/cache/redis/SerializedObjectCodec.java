package com.gvstave.mistergift.data.service.cache.redis;

import com.lambdaworks.redis.codec.RedisCodec;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 */
public class SerializedObjectCodec implements RedisCodec<String, Object> {

    /** The charset. */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * {@inheritDoc}
     */
    @Override
    public String decodeKey(ByteBuffer bytes) {
        return charset.decode(bytes).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object decodeValue(ByteBuffer bytes) {
        try {
            byte[] array = new byte[bytes.remaining()];
            bytes.get(array);
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(array));
            return is.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuffer encodeKey(String key) {
        return ByteBuffer.wrap(charset.encode(key).array());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuffer encodeValue(Object value) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(value);
            return ByteBuffer.wrap(bytes.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

}