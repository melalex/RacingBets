package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.SimpleEntity;

/**
 * Class that represents track surface condition type.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - track surface condition id in data storage</li>
 *      <li>name - track surface condition type name</li>
 * </ul>
 *
 * @link https://en.wikipedia.org/wiki/Going_(horse_racing)#United_Kingdom_and_Ireland
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class TrackCondition extends SimpleEntity {

    @Override
    public String toString() {
        return "TrackCondition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
