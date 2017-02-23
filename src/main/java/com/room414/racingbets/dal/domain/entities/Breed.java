package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.SimpleEntity;


/**
 * Class that represents horse breed.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - breed id in data storage</li>
 *      <li>roleName - breed name</li>
 * </ul>
 *
 * @link https://en.wikipedia.org/wiki/List_of_horse_breeds
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Breed extends SimpleEntity {
    @Override
    public String toString() {
        return "Breed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
