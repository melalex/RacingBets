package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Class that represents user role.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - role id</li>
 *      <li>name - role name</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Role implements Serializable {
    private int id;
    private String name;

    public Role() {

    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        if (id != role.id) {
            return false;
        }

        if (name != null ? !name.equals(role.name) : role.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
