package com.gvstave.mistergift.service.misc;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    /**
     * Returns the slugged name of the input.
     *
     * @param input The input name.
     * @return The slugged output.
     */
    public static String toSlug(final String input) {
        final String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        final String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        final String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
