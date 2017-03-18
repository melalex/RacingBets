package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.dal.domain.enums.Role;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for ApplicationUser class
 *
 * @see com.room414.racingbets.dal.domain.entities.ApplicationUser
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = 6029712881413191567L;

    private long id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isEmailConfirmed;
    private BigDecimal balance;
    private Set<Role> roles = new HashSet<>();

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Role> getRoles() {
        if (roles != null) {
            return new HashSet<>(roles);
        } else {
            return new HashSet<>();
        }
    }

    public void setRoles(Set<Role> roles) {
        if (roles != null) {
            this.roles = new HashSet<>(roles);
        } else {
            this.roles = null;
        }
    }

    public boolean isInRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDto that = (UserDto) o;

        if (id != that.id) {
            return false;
        }

        if (isEmailConfirmed != that.isEmailConfirmed) {
            return false;
        }

        if (login != null ? !login.equals(that.login) : that.login != null) {
            return false;
        }

        if (email != null ? !email.equals(that.email) : that.email != null) {
            return false;
        }

        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }

        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) {
            return false;
        }

        if (balance != null ? balance.compareTo(that.balance) != 0 : that.balance != null) {
            return false;
        }

        if (roles != null ? !roles.equals(that.roles) : that.roles != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        long temp;
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (isEmailConfirmed ? 1 : 0);

        temp = balance != null ? Double.doubleToLongBits(balance.doubleValue()) : 0;
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        result = 31 * result + (roles != null ? roles.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isEmailConfirmed=" + isEmailConfirmed +
                ", balance=" + balance +
                ", roles=" + roles +
                '}';
    }
}
