package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.bll.dto.base.PersonDto;

/**
 * DTO for Owner class
 *
 * @see com.room414.racingbets.dal.domain.entities.Owner
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class OwnerDto extends PersonDto {
    private static final long serialVersionUID = 3071894574677370238L;

    @Override
    public String toString() {
        return "OwnerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
