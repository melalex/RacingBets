package com.room414.racingbets.dal.abstraction.entities;

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

    protected long id;
    protected String firstName;
    protected String lastName;
    protected Date birthday;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
