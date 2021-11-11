package com.xIsm4.language.api.translations;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Translation {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("(\\{[a-zA-Z][a-zA-Z0-9_-]+\\})");
    private final String key;
    private final String message;

    public Translation(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public Collection<String> getPlaceholders() {
        return Collections.unmodifiableCollection(this.findPlaceholders());
    }

    private List<String> findPlaceholders() {
        final List<String> placeholders = new LinkedList<>();
        final Matcher matcher = PLACEHOLDER_PATTERN.matcher(message);

        while (matcher.find()) {
            placeholders.add(matcher.group());
        }

        return placeholders;
    }
}