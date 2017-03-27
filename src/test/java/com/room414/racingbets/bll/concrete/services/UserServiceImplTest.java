package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
class UserServiceImplTest {

    @Test
    void passwordHashingTest() throws IllegalAccessException, NoSuchFieldException {
        List<ApplicationUser> users = new LinkedList<>();

        ApplicationUserDao applicationUserDaoMock = Mockito.mock(ApplicationUserDao.class);
        UnitOfWork unitOfWorkMock = Mockito.mock(UnitOfWork.class);
        UnitOfWorkFactory unitOfWorkMockFactory = Mockito.mock(UnitOfWorkFactory.class);
        Mockito.when(unitOfWorkMockFactory.createUnitOfWork()).then(i -> unitOfWorkMock);
        Mockito.when(unitOfWorkMock.getApplicationUserDao()).then(i -> applicationUserDaoMock);
        Mockito.when(applicationUserDaoMock.findByLoginAndEmail(any(), any())).then(i -> new LinkedList<ApplicationUser>());
        Mockito.doAnswer(i -> users.add((ApplicationUser) i.getArguments()[0]))
                .when(applicationUserDaoMock)
                .create(any());
        Mockito.when(applicationUserDaoMock.findByLogin(any())).then(i -> users.get(0));

        UserService service = new UserServiceImpl(unitOfWorkMockFactory, "SHA1PRNG");
        DozerBeanMapper mapper = new DozerBeanMapper();

        mapper.setMappingFiles(Collections.singletonList("META-INF/dozerBeanMapping.xml"));

        Field mapperField = UserServiceImpl.class.getDeclaredField("mapper");
        mapperField.setAccessible(true);
        mapperField.set(service, mapper);

        UserDto userDto = new UserDto();
        userDto.setLogin("login");
        userDto.setEmail("email@gmil.com");
        userDto.setPassword("password");

        service.create(userDto);

        assert service.findByLoginPassword("login", "password") != null;
    }

}