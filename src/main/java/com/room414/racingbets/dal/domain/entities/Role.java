package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.SimpleEntity;

/**
 * Class that represents user role.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - role id in data storage</li>
 *      <li>name - role name</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Role extends SimpleEntity {
    private static final long serialVersionUID = 487854808117130494L;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
