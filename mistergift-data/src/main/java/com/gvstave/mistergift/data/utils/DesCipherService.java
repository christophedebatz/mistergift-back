package com.gvstave.mistergift.data.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Encrypt and decrypt with DES algorithm.
 */
public class DesCipherService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(DesCipherService.class);

    /** The cipher key (can be common for app or dynamic). */
    private String key;

    /** The used algorithm. */
    private String algorithm;

    /** The des cipher. */
    private Cipher desCipher;

    /**
     *
     * @throws NoSuchAlgorithmException
     */
    public DesCipherService (String key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Objects.requireNonNull(key);
        Objects.requireNonNull(algorithm);
        this.key = key;
        this.algorithm = algorithm.toUpperCase();
    }

    /**
     *
     * @param phrase
     * @return
     */
    public String encrypt(String phrase) {
        Objects.requireNonNull(phrase);
        try {
            desCipher = Cipher.getInstance(algorithm);
            desCipher.init(Cipher.ENCRYPT_MODE, generateKey(key, algorithm));
            byte[] rawBytes = desCipher.doFinal(phrase.getBytes(StandardCharsets.UTF_8));
            return new String(rawBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("Unable to encrypt string={} with DES", phrase, e);
        }
        return null;
    }

    /**
     *
     * @param raw
     * @return
     */
    public String decrypt(String raw) {
        Objects.requireNonNull(raw);
        try {
            desCipher = Cipher.getInstance(algorithm);
            desCipher.init(Cipher.DECRYPT_MODE, generateKey(key, algorithm));
            byte[] rawBytes = desCipher.doFinal(raw.getBytes(StandardCharsets.UTF_8));
            return new String(rawBytes);
        } catch (Exception e) {
            LOGGER.error("Unable to decrypt with DES", e);
            return null;
        }
    }

    /**
     *
     * @param key
     * @param algorithm
     * @return
     * @throws Exception
     */
    private Key generateKey(String key, String algorithm) throws Exception {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
    }

}
