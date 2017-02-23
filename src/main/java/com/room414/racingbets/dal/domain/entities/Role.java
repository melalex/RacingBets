package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Class that represents user role.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - role id</li>
 *      <li>roleName - role roleName</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Role implements Serializable {
    private int id;
    private String roleName;

    public Role() {

    }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

        if (roleName != null ? !roleName.equals(role.roleName) : role.roleName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
