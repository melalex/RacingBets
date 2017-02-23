package com.room414.racingbets.dal.domain.entities;

/**
 * Class that represents country entity
 * <p>name - country name</p>
 * <p>code - ISO 3166 country code</p>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Country {
    private int id;
    private String name;
    private String code;

    public Country() {

    }

    public Country(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        if (id != country.id) {
            return false;
        }

        if (name != null ? !name.equals(country.name) : country.name != null) {
            return false;
        }

        return code != null ? code.equals(country.code) : country.code == null;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);

        return result;
    }
}
