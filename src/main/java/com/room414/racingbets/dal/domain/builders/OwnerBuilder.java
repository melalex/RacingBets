package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.domain.entities.Owner;

/**
 * Simplify creating Owner instance using builder pattern.
 *
 * @see Owner
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class OwnerBuilder extends PersonBuilder<Owner> {

    @Override
    protected Owner getPerson() {
        return new Owner();
    }
}
