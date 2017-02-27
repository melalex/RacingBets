package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Simplify creating ApplicationUser instance using builder pattern.
 *
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public class ApplicationUserBuilder {
    private ApplicationUser applicationUser = new ApplicationUser();

    public void setId(int id) {
        applicationUser.setId(id);
    }

    public void setLogin(String login) {
        applicationUser.setLogin(login);
    }

    public void setEmail(String email) {
        applicationUser.setEmail(email);
    }

    public void setPassword(String password) {
        applicationUser.setPassword(password);
    }

    public void setFirstName(String firstName) {
        applicationUser.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        applicationUser.setLastName(lastName);
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        applicationUser.setEmailConfirmed(emailConfirmed);
    }

    public void setBalance(BigDecimal balance) {
        applicationUser.setBalance(balance);
    }

    public void setRoles(Set<Role> roles) {
        applicationUser.setRoles(roles);
    }

    public ApplicationUser build() {
        return applicationUser;
    }
}
