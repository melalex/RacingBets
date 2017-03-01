package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.domain.builders.TrainerBuilder;

/**
 * Horse trainer.
 * To create instances of Trainer is recommended to use the TrainerBuilder.
 *
 * @see TrainerBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Trainer extends Person {
    private static final long serialVersionUID = -7009460944562959560L;

    public static TrainerBuilder builder() {
        return new TrainerBuilder();
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", country=" + country +
                '}';
    }
}
