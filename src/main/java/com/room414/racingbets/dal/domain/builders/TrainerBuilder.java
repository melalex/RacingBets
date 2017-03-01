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
public class TrainerBuilder extends PersonBuilder {
    @Override
    public Trainer build() {
        Trainer trainer = new Trainer();

        trainer.setId(id);
        trainer.setFirstName(firstName);
        trainer.setLastName(secondName);
        trainer.setBirthday(birthday);
        trainer.setCountry(country);

        return trainer;
    }
}
