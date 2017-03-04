package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
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
public interface ApplicationUserDao extends CrudDao<Long, ApplicationUser> {
    // TODO: is good?? (find and count method)
    /**
     * Search ApplicationUser by prefix.
     *
     * @param loginPart begin of ApplicationUser login
     * @param offset the number of items that need to skip
     * @param limit elements count in result
     * @return List of ApplicationUsers whose login starts with loginPart or empty if no found.
     */
    List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException;

    /**
     * @param loginPart begin of ApplicationUser login
     * @return count ApplicationUsers whose login starts with loginPart
     */
    long findByLoginPartCount(String loginPart) throws DalException;

    /**
     * @param login ApplicationUser login
     * @param password ApplicationUser password
     * @return ApplicationUser with login == login param
     */
    ApplicationUser findByLoginAndPassword(String login, String password) throws DalException;

    /**
     * Set ApplicationUser isEmailConfirmed field to true to user with id == id param
     *
     * @param id ApplicationUser id
     */
    boolean confirmEmail(long id) throws DalException;

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
