package com.gvstave.mistergift.service.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

/**
 * Translates given text.
 * Mapper of {@link MessageSource}.
 */
@Component
public class Translator {

    /** The message source. */
    @Inject
    private MessageSource messageSource;

    /**
     * Returns the translation for given key and arguments.
     *
     * @param key The key.
     * @param args The args.
     * @return The associated translation.
     */
    public String translate(String key, List<String> args) {
        Objects.requireNonNull(key);
        Object[] objects = (args != null && !args.isEmpty()) ? args.toArray() : null;

        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }

    /**
     * Returns the translation of given key.
     *
     * @param key The key.
     * @return The associated translation.
     */
    public String translate(String key) {
        return translate(key, null);
    }
}
