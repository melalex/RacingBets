package com.room414.racingbets.dal.domain.enums;

/**
 * Preferred language of {@code ApplicationUser}
 *
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public enum  Language {
    ENGLISH("en"),
    RUSSIAN("ru");

    /**
     * An ISO 639 alpha-2 or alpha-3 language code
     */
    private String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Language getLanguage(String name) {
        for(Language v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no Language with code " + name);
    }

}