package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO for ApplicationUser entity
 *
 * @see ApplicationUser
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface ApplicationUserDao extends SearchDao<Long, ApplicationUser> {
    /**
     * @param login ApplicationUser login
     * @return UserDto with login == login param
     */
    ApplicationUser findByLogin(String login);


    /**
     * @param login ApplicationUser login
     * @param email ApplicationUser email
     * @return ApplicationUsers who's email == email param or login == login param
     */
    List<ApplicationUser> findByLoginAndEmail(String login, String email);

    /**
     * Set ApplicationUser isEmailConfirmed field to true to user with id == id param
     *
     * @param id ApplicationUser id
     */
    void confirmEmail(long id);

    /**
     * Add role to ApplicationUser
     *
     * @param userId ApplicationUser id
     * @param role role
     */
    void addRole(long userId, Role role);

    /**
     * Remove role from ApplicationUser
     *
     * @param userId ApplicationUser id
     * @param role role
     */
    void removeRole(long userId, Role role);

    /**
     * @param id ApplicationUser id
     * @param amount amount of money
     * @return true if the money were removed successfully, false if not enough money on the balance sheet
     */
    boolean tryGetMoney(long id, BigDecimal amount);

    /**
     * Add amount param to ApplicationUser balance field
     *
     * @param id ApplicationUser id
     * @param amount amount of money
     */
    void putMoney(long id, BigDecimal amount);
}
