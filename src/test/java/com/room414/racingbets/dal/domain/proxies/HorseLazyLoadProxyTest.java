package com.room414.racingbets.dal.domain.proxies;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.builders.HorseBuilder;
import com.room414.racingbets.dal.domain.enums.Gender;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Date;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
class HorseLazyLoadProxyTest {
    @Test
    void equals_proxyToProxy_true() throws DalException, NoSuchFieldException, IllegalAccessException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseLazyLoadProxy proxy1 = HorseLazyLoadProxy.create(1);
        HorseLazyLoadProxy proxy2 = HorseLazyLoadProxy.create(1);

        Field f = HorseLazyLoadProxy.class.getDeclaredField("horse");
        f.setAccessible(true);
        f.set(proxy1, horse);
        f.set(proxy2, horse);

        assert proxy1.equals(proxy2);
    }

    @Test
    void equals_proxyToProxy_false() throws DalException, NoSuchFieldException, IllegalAccessException {
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

        HorseLazyLoadProxy proxy1 = HorseLazyLoadProxy.create(1);
        HorseLazyLoadProxy proxy2 = HorseLazyLoadProxy.create(2);

        Field f = HorseLazyLoadProxy.class.getDeclaredField("horse");
        f.setAccessible(true);
        f.set(proxy1, horse1);
        f.set(proxy2, horse2);

        assert !proxy1.equals(proxy2);
    }

    @Test
    void equals_proxyToHorse_true() throws DalException, NoSuchFieldException, IllegalAccessException {
        HorseBuilder horseBuilder = new HorseBuilder();
        Horse horse = horseBuilder
                .setBirthday(new Date(1488299335))
                .setId(1)
                .setGender(Gender.MARE)
                .setName("Mel")
                .setOwnerById(1)
                .setTrainerById(1)
                .build();

        HorseLazyLoadProxy proxy1 = HorseLazyLoadProxy.create(1);

        Field f = HorseLazyLoadProxy.class.getDeclaredField("horse");
        f.setAccessible(true);
        f.set(proxy1, horse);

        assert proxy1.equals(horse);
    }

    @Test
    void equals_proxyToHorse_false() throws DalException, NoSuchFieldException, IllegalAccessException {
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

        HorseLazyLoadProxy proxy1 = HorseLazyLoadProxy.create(1);

        Field f = HorseLazyLoadProxy.class.getDeclaredField("horse");
        f.setAccessible(true);
        f.set(proxy1, horse1);

        assert !proxy1.equals(horse2);
    }

    @Test
    void create_zeroId_returnedNull() {
        HorseLazyLoadProxy proxy = HorseLazyLoadProxy.create(0);

        assert proxy == null : "proxy != null";
    }
}