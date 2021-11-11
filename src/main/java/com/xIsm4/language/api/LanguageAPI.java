package com.xIsm4.language.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xIsm4.language.api.translations.Translation;
import com.xIsm4.language.api.translations.TranslationSet;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class LanguageAPI {
    private static final Gson gson = new Gson();
    private static final Map<Locale, TranslationSet> translationSets = new HashMap<>();

    private LanguageAPI() {
        // Prevent instantiation.
    }

    public static TranslationSet getTranslations(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        TranslationSet translationSet = null;

        if (!translationSets.containsKey(locale)) {
            translationSet = LanguageAPI.loadTranslations(locale);
            translationSets.putIfAbsent(locale, translationSet);
        }

        return translationSets.get(locale);
    }

    public static TranslationSet getDefaultTranslations() {
        return LanguageAPI.getTranslations(null);
    }

    private static TranslationSet loadTranslations(Locale locale) {
        File file = new File(String.format("%s.json", locale.toLanguageTag()));

        if (!file.exists()) {
            try {
                file = new File(Objects.requireNonNull(LanguageAPI.class.getClassLoader().getResource(file.getName())).toURI());

                if (!file.exists()) {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException | URISyntaxException | NullPointerException e) {
                throw new IllegalStateException("Could not load internal file.");
                //Trow exception
            }
        }

        try (InputStreamReader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            final Set<Translation> translations = new HashSet<>();
            final JsonObject obj = gson.fromJson(in, JsonObject.class);

            for (String key : obj.keySet()) {
                translations.add(new Translation(key, obj.get(key).getAsString()));
            }

            return new TranslationSet(translations);
        } catch (IOException e) {
           throw new IllegalStateException(String.format("Cannot read '%s'.", file.getName()));
        }
    }
}
