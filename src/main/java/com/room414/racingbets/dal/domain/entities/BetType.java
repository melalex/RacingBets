package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.SimpleEntity;

/**
 * Class that represents bet type.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - bet type id in data storage</li>
 *      <li>name - bet type name</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 24 Feb 2017
 */
// TODO: remove this class
public class BetType extends SimpleEntity {
    private static final long serialVersionUID = -8773086214148701974L;

    @Override
    public String toString() {
        return "BetType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';

    }
}
