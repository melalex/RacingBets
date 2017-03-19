package com.room414.racingbets.bll.dto.entities;

import java.io.Serializable;

/**
 * DTO for Racecourse class
 *
 * @see com.room414.racingbets.dal.domain.entities.Racecourse
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class RacecourseDto implements Serializable {
    private static final long serialVersionUID = 118102506777669109L;

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private String contact;
    private String clerk;

    public RacecourseDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        RacecourseDto that = (RacecourseDto) o;

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

        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);

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
        return "RacecourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", contact='" + contact + '\'' +
                ", clerk='" + clerk + '\'' +
                '}';
    }
}
