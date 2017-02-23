package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Person;

/**
 * Horse trainer.
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Trainer extends Person {
    private static final long serialVersionUID = -7009460944562959560L;

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthday=" + birthday +
                ", country=" + country +
                '}';
    }
}
