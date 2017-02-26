package com.room414.racingbets.dal.abstraction.entities;

import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.sql.Date;

/**
 * Base class to Horse and its proxies.
 *
 * @see com.room414.racingbets.dal.domain.entities.Horse
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public abstract class Horse {
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
    public abstract com.room414.racingbets.dal.domain.entities.Horse.Gender getGender();
    public abstract void setGender(com.room414.racingbets.dal.domain.entities.Horse.Gender gender);
    public abstract com.room414.racingbets.dal.domain.entities.Horse getSir();
    public abstract void setSir(com.room414.racingbets.dal.domain.entities.Horse sir);
    public abstract com.room414.racingbets.dal.domain.entities.Horse getDam();
    public abstract void setDam(com.room414.racingbets.dal.domain.entities.Horse dam);
}
