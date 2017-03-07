package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.domain.builders.JockeyBuilder;

/**
 * Horse jockey.
 * To create instances of Jockey is recommended to use the JockeyBuilder.
 *
 * @see JockeyBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Jockey extends Person {
    private static final long serialVersionUID = 3949892361764703963L;

    public static JockeyBuilder builder() {
        return new JockeyBuilder();
    }

    @Override
    public String toString() {
        return "Jockey{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
