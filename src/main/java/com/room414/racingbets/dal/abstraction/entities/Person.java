package com.room414.racingbets.dal.abstraction.entities;

import com.room414.racingbets.dal.domain.entities.Country;

import java.io.Serializable;
import java.sql.Date;

/**
 * Base class for entities that stores data about person.
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = -5384092688130247272L;

    protected int id;
    protected String firstName;
    protected String secondName;
    protected Date birthday;
    /**
     * Person's country.
     *
     * @see Country
     */
    protected Country country;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (id != person.id) {
            return false;
        }

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) {
            return false;
        }

        if (secondName != null ? !secondName.equals(person.secondName) : person.secondName != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) {
            return false;
        }

        if (country != null ? !country.equals(person.country) : person.country != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthday=" + birthday +
                ", country=" + country +
                '}';
    }
}
