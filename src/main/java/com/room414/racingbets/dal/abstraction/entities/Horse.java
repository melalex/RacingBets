package com.room414.racingbets.dal.abstraction.entities;

import com.room414.racingbets.dal.domain.builders.HorseBuilder;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.io.Serializable;
import java.sql.Date;

/**
 * Base class to HorseEntity and its proxies.
 *
 * @see com.room414.racingbets.dal.domain.entities.HorseEntity
 * @see com.room414.racingbets.dal.domain.proxies.HorseLazyLoadProxy
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public abstract class Horse implements Serializable{
    private static final long serialVersionUID = -4727421296999383932L;

    // TODO: is cool?
    public static HorseBuilder builder() {
        return new HorseBuilder();
    }

    public abstract int getId();
    public abstract void setId(int id);
    public abstract String getName();
    public abstract void setName(String name);
    public abstract Trainer getTrainer();
    public abstract void setTrainer(Trainer trainer);
    public abstract Owner getOwner();
    public abstract void setOwner(Owner owner);
    public abstract Date getBirthday();
    public abstract void setBirthday(Date birthday);
    public abstract Gender getGender();
    public abstract void setGender(Gender gender);
    public abstract Horse getSir();
    public abstract void setSir(Horse sir);
    public abstract Horse getDam();
    public abstract void setDam(Horse dam);
}
