package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.domain.entities.Jockey;

/**
 * Simplify creating Jockey instance using builder pattern.
 *
 * @see Jockey
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JockeyBuilder extends PersonBuilder<Jockey> {

    @Override
    protected Jockey getPerson() {
        return new Jockey();
    }
}
