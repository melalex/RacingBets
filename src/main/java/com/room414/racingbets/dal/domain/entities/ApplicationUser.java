package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.builders.ApplicationUserBuilder;
import com.room414.racingbets.dal.domain.enums.Language;
import com.room414.racingbets.dal.domain.enums.Role;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a system actor.
 * To create instances of ApplicationUser is recommended to use the ApplicationUserBuilder.
 *
 * @see com.room414.racingbets.dal.domain.builders.ApplicationUserBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class ApplicationUser implements Serializable {
    private static final long serialVersionUID = 6029712881413191567L;

    private long id;
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
    private Language language;

    public ApplicationUser() {
    }

    public static ApplicationUserBuilder builder() {
        return new ApplicationUserBuilder();
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

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public boolean isInRole(Role role) {
        return roles.contains(role);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationUser user = (ApplicationUser) o;

        if (id != user.id) {
            return false;
        }

        if (isEmailConfirmed != user.isEmailConfirmed) {
            return false;
        }

        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }

        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }

        if (password != null ? !password.equals(user.password) : user.password != null) {
            return false;
        }

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return false;
        }

        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return false;
        }

        if (balance != null ? !balance.equals(user.balance) : user.balance != null) {
            return false;
        }

        if (roles != null ? !roles.equals(user.roles) : user.roles != null) {
            return false;
        }

        if (language != user.language) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (isEmailConfirmed ? 1 : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);

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
                ", language=" + language +
                '}';
    }

}
