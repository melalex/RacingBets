package com.room414.racingbets.web.model.viewmodels;

import com.room414.racingbets.dal.domain.enums.Language;
import com.room414.racingbets.dal.domain.enums.Role;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class UserViewModel implements Serializable {
    private static final long serialVersionUID = 8290610006623911735L;

    private long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isEmailConfirmed;
    private BigDecimal balance;
    private Set<Role> roles = new HashSet<>();
    private Language language;

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
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserViewModel that = (UserViewModel) o;

        if (id != that.id) return false;
        if (isEmailConfirmed != that.isEmailConfirmed) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;
        return language == that.language;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
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
        return "UserViewModel{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isEmailConfirmed=" + isEmailConfirmed +
                ", balance=" + balance +
                ", roles=" + roles +
                ", language=" + language +
                ", emailConfirmed=" + isEmailConfirmed() +
                '}';
    }
}
