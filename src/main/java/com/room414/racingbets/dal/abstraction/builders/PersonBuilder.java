package com.room414.racingbets.dal.abstraction.builders;

import com.room414.racingbets.dal.abstraction.entities.Person;

import java.sql.Date;

/**
 * Base class for builders that creates person subclasses.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public abstract class PersonBuilder<T extends Person> {
    protected long id;
    protected String firstName;
    protected String secondName;
    protected Date birthday;

    protected abstract T getPerson();

    public PersonBuilder<T> setId(long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder<T> setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder<T> setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public PersonBuilder<T> setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public T build() {
        T person = getPerson();

        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(secondName);
        person.setBirthday(birthday);

        return person;
    }
}
