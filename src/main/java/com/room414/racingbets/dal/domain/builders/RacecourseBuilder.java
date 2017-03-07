package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Racecourse;

/**
 * Simplify creating Racecourse instance using builder pattern.
 *
 * @see Racecourse
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class RacecourseBuilder {
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private String contact;
    private String clerk;

    public RacecourseBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public RacecourseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RacecourseBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public RacecourseBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public RacecourseBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public RacecourseBuilder setClerk(String clerk) {
        this.clerk = clerk;
        return this;
    }

    public Racecourse build() {
        Racecourse racecourse = new Racecourse();

        racecourse.setId(id);
        racecourse.setName(name);
        racecourse.setLatitude(latitude);
        racecourse.setLongitude(longitude);
        racecourse.setContact(contact);
        racecourse.setClerk(clerk);

        return racecourse;
    }
}