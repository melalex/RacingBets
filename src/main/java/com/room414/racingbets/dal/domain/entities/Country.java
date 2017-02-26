package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Class that represents country entity
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Country implements Serializable {
    private static final long serialVersionUID = 8904582879716615954L;

    /**
     * Country id in data storage
     */
    private int id;
    /**
     * Country name
     */
    private String name;
    /** ISO 3166 country code.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_3166">ISO 3166</a>
     */
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

        if (code != null ? !code.equals(country.code) : country.code != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
