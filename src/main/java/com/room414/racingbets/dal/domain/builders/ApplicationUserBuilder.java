package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Simplify creating ApplicationUser instance using builder pattern.
 *
 * @see ApplicationUser
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public class ApplicationUserBuilder {
    private int id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isEmailConfirmed;
    private BigDecimal balance;
    private Set<Role> roles;

    private Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public ApplicationUserBuilder setId(int id) {
        this.id = id ;
        return this;
    }

    public ApplicationUserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public ApplicationUserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApplicationUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public ApplicationUserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ApplicationUserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ApplicationUserBuilder setEmailConfirmed(boolean emailConfirmed) {
        this.isEmailConfirmed = emailConfirmed;
        return this;
    }

    public ApplicationUserBuilder setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public ApplicationUserBuilder setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public ApplicationUserBuilder addRole(Role role) {
        getRoles().add(role);
        return this;
    }

    public ApplicationUser build() {
        ApplicationUser applicationUser = new ApplicationUser();

        applicationUser.setId(id);
        applicationUser.setLogin(login);
        applicationUser.setPassword(password);
        applicationUser.setEmail(email);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setBalance(balance);
        applicationUser.setEmailConfirmed(isEmailConfirmed);
        applicationUser.setRoles(getRoles());

        return applicationUser;
    }
}