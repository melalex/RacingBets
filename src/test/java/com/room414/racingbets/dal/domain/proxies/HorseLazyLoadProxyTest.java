package com.room414.racingbets.dal.domain.proxies;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.builders.HorseBuilder;
import com.room414.racingbets.dal.domain.enums.Gender;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
class HorseLazyLoadProxyTest {
    @Test
    void equals_proxyToProxy_true() throws DalException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseDao mockDao = mock(HorseDao.class);
        when(mockDao.find(1L)).thenReturn(horse);

        HorseLazyLoadProxy proxy1 = new HorseLazyLoadProxy(1, mockDao);
        HorseLazyLoadProxy proxy2 = new HorseLazyLoadProxy(1, mockDao);

        assert proxy1.equals(proxy2);
    }

    @Test
    void equals_proxyToProxy_false() throws DalException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse1 = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();
        Horse horse2 = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(2)
                .setGender(Gender.MARE)
                .setName("Al")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseDao mockDao = mock(HorseDao.class);
        when(mockDao.find(1L)).thenReturn(horse1);
        when(mockDao.find(2L)).thenReturn(horse2);

        HorseLazyLoadProxy proxy1 = new HorseLazyLoadProxy(1, mockDao);
        HorseLazyLoadProxy proxy2 = new HorseLazyLoadProxy(2, mockDao);

        assert !proxy1.equals(proxy2);
    }

    @Test
    void equals_proxyToHorse_true() throws DalException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseDao mockDao = mock(HorseDao.class);
        when(mockDao.find(1L)).thenReturn(horse);

        HorseLazyLoadProxy proxy1 = new HorseLazyLoadProxy(1, mockDao);

        assert proxy1.equals(horse);
    }

    @Test
    void equals_proxyToHorse_false() throws DalException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse1 = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();
        Horse horse2 = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(2)
                .setGender(Gender.MARE)
                .setName("Al")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseDao mockDao = mock(HorseDao.class);
        when(mockDao.find(1L)).thenReturn(horse1);

        HorseLazyLoadProxy proxy1 = new HorseLazyLoadProxy(1, mockDao);

        assert !proxy1.equals(horse2);
    }
}