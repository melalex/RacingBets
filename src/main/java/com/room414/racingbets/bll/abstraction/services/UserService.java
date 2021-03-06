package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.CheckResult;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
// TODO: roles and user relationship conflict
public interface UserService {
    CheckResult checkLoginAndEmail(String login, String email);
    void create(UserDto user);
    void setRoles(long id, Set<Role> roles);

    void update(UserDto user);
    void delete(long id);
    void confirmEmail(long userId);
    void putMoney(long id, BigDecimal amount);

    UserDto find(long id);
    UserDto findByLoginPassword(String login, String password);
    List<UserDto> findAll(Pager pager);
    List<UserDto> search(String login, Pager pager);

    boolean tryGetMoney(long id, BigDecimal betSize);
}
