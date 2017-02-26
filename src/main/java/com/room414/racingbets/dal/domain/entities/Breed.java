package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;


/**
 * Class that represents horse breed.
 *
 * @link https://en.wikipedia.org/wiki/List_of_horse_breeds
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Breed implements Serializable {

    private int id;
    private String name;

    public Breed() {

    }

    public Breed(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Breed that = (Breed) o;

        if (id != that.id) {
            return false;
        }

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
