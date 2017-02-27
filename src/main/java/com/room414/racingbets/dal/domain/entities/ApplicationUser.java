package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.enums.Role;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a system actor.
 *
 * @see com.room414.racingbets.dal.domain.builders.ApplicationUserBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class ApplicationUser implements Serializable {
    private static final long serialVersionUID = 6029712881413191567L;

    private int id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    /**
     * It indicates whether the user has confirmed email.
     */
    private boolean isEmailConfirmed;
    /**
     * The cash balance of the user.
     */
    private BigDecimal balance;
    /**
     * Roles applied to concrete User.
     */
    private Set<Role> roles = new HashSet<>();

    public ApplicationUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        Set<Role> result = new HashSet<>();
        result.addAll(roles);
        return result;
    }

    public void setRoles(Set<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationUser that = (ApplicationUser) o;

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

        if (balance != null ? !balance.equals(that.balance) : that.balance != null) {
            return false;
        }

        if (!roles.equals(that.roles)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (isEmailConfirmed ? 1 : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + roles.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
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
