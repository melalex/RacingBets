package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.domain.builders.OwnerBuilder;

/**
 * Horse owner.
 * To create instances of Owner is recommended to use the OwnerBuilder.
 *
 * @see OwnerBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Owner extends Person {
    private static final long serialVersionUID = 3071894574677370238L;

    public static OwnerBuilder builder() {
        return new OwnerBuilder();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
