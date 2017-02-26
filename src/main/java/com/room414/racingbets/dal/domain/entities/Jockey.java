package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Person;

/**
 * Horse jockey.
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Jockey extends Person {
    private static final long serialVersionUID = 3949892361764703963L;

    @Override
    public String toString() {
        return "Jockey{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthday=" + birthday +
                ", country=" + country +
                '}';
    }
}