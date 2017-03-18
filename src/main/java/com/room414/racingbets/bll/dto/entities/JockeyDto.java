package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.bll.dto.base.PersonDto;

/**
 * DTO for Jockey class
 *
 * @see com.room414.racingbets.dal.domain.entities.Jockey
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class JockeyDto extends PersonDto {
    private static final long serialVersionUID = 3949892361764703963L;

    @Override
    public String toString() {
        return "JockeyDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
