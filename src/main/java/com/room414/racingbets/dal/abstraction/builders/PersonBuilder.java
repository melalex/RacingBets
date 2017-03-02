package com.room414.racingbets.dal.abstraction.builders;

import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.domain.entities.Country;

import java.sql.Date;

/**
 * Base class for builders that creates person subclasses.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public abstract class PersonBuilder {
    protected long id;
    protected String firstName;
    protected String secondName;
    protected Date birthday;
    protected Country country;

    public void setId(long id) {
        this.id = id;
    }

    public PersonBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public PersonBuilder setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public PersonBuilder setCountry(Country country) {
        this.country = country;
        return this;
    }

    public PersonBuilder setCountryById(int id) {
        this.country = new Country();
        this.country.setId(id);
        return this;
    }

    public abstract Person build();
}
