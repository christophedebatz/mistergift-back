package com.gvstave.mistergift.service.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
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
     * @param locale The locale.
     * @return The associated translation.
     */
    public String translate(String key, List<String> args, Locale locale) {
        Objects.requireNonNull(key);
        Object[] objects = (args != null && !args.isEmpty()) ? args.toArray() : null;
        Locale forceLocale = (locale != null) ? locale : LocaleContextHolder.getLocale();

        return messageSource.getMessage(key, objects, forceLocale);
    }

    /**
     * Returns the translation of given key.
     *
     * @param key The key.
     * @return The associated translation.
     */
    public String translate(String key) {
        return translate(key, null, null);
    }
}
