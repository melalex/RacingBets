package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.domain.entities.Trainer;

/**
 * Simplify creating Trainer instance using builder pattern.
 *
 * @see Trainer
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class TrainerBuilder extends PersonBuilder<Trainer> {

    @Override
    protected Trainer getPerson() {
        return new Trainer();
    }
}
