package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Stores information about racecourse.
 * To create instances of Race is recommended to use the RaceBuilder.
 *
 * @see com.room414.racingbets.dal.domain.builders.RacecourseBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Racecourse implements Serializable {
    private static final long serialVersionUID = 2069863490131436051L;

    private int id;
    private String name;
    private Country country;
    private double latitude;
    private double longitude;
    /**
     * Racecourse telephone number.
     */
    private String contact;
    /**
     * Clerk of the racecourse
     */
    private String clerk;

    public Racecourse() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Racecourse that = (Racecourse) o;

        if (id != that.id) {
            return false;
        }

        if (Double.compare(that.latitude, latitude) != 0) {
            return false;
        }

        if (Double.compare(that.longitude, longitude) != 0) {
            return false;
        }

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        if (country != null ? !country.equals(that.country) : that.country != null) {
            return false;
        }

        if (contact != null ? !contact.equals(that.contact) : that.contact != null) {
            return false;
        }

        if (clerk != null ? !clerk.equals(that.clerk) : that.clerk != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (clerk != null ? clerk.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Racecourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", contact='" + contact + '\'' +
                ", clerk='" + clerk + '\'' +
                '}';
    }
}
