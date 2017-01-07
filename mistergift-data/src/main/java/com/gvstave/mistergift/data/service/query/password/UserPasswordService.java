package com.gvstave.mistergift.data.service.query.password;


import com.gvstave.mistergift.data.cache.CacheService;
import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.query.TokenService;
import com.gvstave.mistergift.data.service.utils.DesCipherService;
import com.gvstave.mistergift.service.mailing.UserPasswordEmailService;
import com.gvstave.mistergift.service.mailing.exception.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Service to ask new password.
 */
@Service
public class UserPasswordService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserPasswordService.class);

    /** The expiration delay of user password token. */
    private static final long PASSWORD_TOKEN_TTL = 3600;

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user password email service. */
    @Inject
    private UserPasswordEmailService emailService;

    /** The cache service. */
    @Inject
    private CacheService cacheService;

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /** The environment. */
    @Inject
    private Environment env;

    /** The password encoder. */
    @Inject
    private PasswordEncoder passwordEncoder;

    /** The cipher service. */
    private DesCipherService cipherService;

    /**
     * Constructor.
     */
    @PostConstruct
    public void initialize() throws InvalidKeyException, NoSuchAlgorithmException {
        cipherService = new DesCipherService(env.getProperty("cipher.key"), env.getProperty("cipher.algorithm"));
    }

    /**
     * Returns the result of user password resetting request.
     *
     * @param email The user email.
     */
    public UserPasswordResult requestNewPassword (String email) {
        Objects.requireNonNull(email);
        UserPasswordResult result = new UserPasswordResult();

        return Optional.ofNullable(userPersistenceService.findByEmail(email)).map(user -> {

            // get token cache key
            String cacheKey = getCachePattern(user.getId());

            try {
                // cipher concatenation of generated token and user id
                String token = cipherService.encrypt(
                    String.format("%s-%s", user.getId(), UUID.randomUUID().toString().replace("-", "")));

                // create mail model
                Map<String, String> model = new HashMap<>();
                model.put("user.name", user.getName());
                model.put("user.password.token", token);
                model.put("user.password.token.expiration", String.valueOf(PASSWORD_TOKEN_TTL));

                // set token in cache and send email with link
                cacheService.set(cacheKey, token, PASSWORD_TOKEN_TTL);
                emailService.send(user.getEmail(), new HashMap<>(), user.getLocale());

            } catch (MailException e) {
                LOGGER.error("Cannot send user password email for user:id={}", user.getId(), e);
                cacheService.delete(cacheKey);
                result.setErrorType("mail");
            } catch (Exception e) {
                LOGGER.error("Cannot store user password token into cache for user:id={}", user.getId(), e);
                result.setErrorType("cache");
            }

            return result;

        }).orElseGet(() -> {
            result.setErrorType("mail");
            return result;
        });
    }

    /**
     * Sets the new user password and returns the renewed user token.
     *
     * @param rawToken The raw token (from email).
     * @return The user token.
     * @throws UserNotFoundException if user has been not found.
     * @throws PasswordTokenNotFound if password token has been not recognized.
     */
    @Transactional
    public Token setNewPassword (String rawToken, String password)
        throws UserNotFoundException, PasswordTokenNotFound, UserIdNotFoundException {
        Objects.requireNonNull(rawToken);

        String token = cipherService.decrypt(rawToken); // decrypt given raw token
        Long userId = extractUserId(token); // retrieve user id from token
        String cacheKey = getCachePattern(userId); // get cache key from token-extracted user

        // if cache contains a password token for this user id
        if (cacheService.exists(cacheKey)) {
            return Optional.ofNullable(userPersistenceService.findOne(userId)).map(user -> {
                user.setPassword(passwordEncoder.encode(password));
                cacheService.delete(cacheKey);
                return tokenService.createUserToken(user);
            }).orElseThrow(() -> new UserNotFoundException(userId));
        }
        throw new PasswordTokenNotFound();
    }

    /**
     * Returns the cache key for user password service.
     *
     * @param userId The user id.
     * @return The user password token key.
     */
    private static String getCachePattern(Long userId) {
        return String.format("user:%s:password:token", userId);
    }

    /**
     * Returns the user id that is contained into the token.
     *
     * @param token The password token.
     * @return The user id.
     */
    private static Long extractUserId(String token) throws UserIdNotFoundException {
        String[] split = token.split("-");
        if (split.length > 0) {
            return Long.valueOf(split[0]);
        }
        throw new UserIdNotFoundException();
    }

}
