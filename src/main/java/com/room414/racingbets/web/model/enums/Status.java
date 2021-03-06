package com.room414.racingbets.web.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public enum Status {
    SUCCESS("success"),
    ERROR("error");

    private String name;

    Status(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Status getStatus(String name) {
        for(Status v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no Status named " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
