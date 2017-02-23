package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.SimpleEntity;

/**
 * Class that represents weather type.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - weather type id in data storage</li>
 *      <li>name - weather type name</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Weather extends SimpleEntity {

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
