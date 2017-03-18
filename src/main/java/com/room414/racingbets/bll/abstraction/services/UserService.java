package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.domain.enums.Role;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface UserService {
    int EMAIL_EXISTS = 0b001;
    int LOGIN_EXISTS = 0b010;
    int SUCCESS = 0b100;

    void delete(long id);
    void update(UserDto user);

    UserDto findByLoginPassword(String login, String password);
    List<UserDto> findByLoginPart(String login);
    int createAccount(UserDto user);
    boolean confirmEmail(long id, String token);

    void addRole(long id, Role role);
    void removeRole(long id, Role role);
}
