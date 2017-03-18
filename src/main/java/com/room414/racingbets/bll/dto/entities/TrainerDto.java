package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.bll.dto.base.PersonDto;

/**
 * DTO for Trainer class
 *
 * @see com.room414.racingbets.dal.domain.entities.Trainer
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class TrainerDto extends PersonDto {
    private static final long serialVersionUID = -7009460944562959560L;

    @Override
    public String toString() {
        return "TrainerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
