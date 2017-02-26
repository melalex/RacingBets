package com.room414.racingbets.dal.domain.proxies;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public class HorseLazyLoadProxy extends Horse {
    private int id;
    private Horse horse;
    private HorseDao horseDao;

    public HorseLazyLoadProxy(int id, HorseDao horseDao) {
        this.id = id;
        this.horseDao = horseDao;
    }

    private Horse getHorse() {
        if (horse == null) {
            // TODO: Lazy load logic
        }
        return horse;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return horse.getName();
    }

    @Override
    public void setName(String name) {
        horse.setName(name);
    }

    @Override
    public Trainer getTrainer() {
        return horse.getTrainer();
    }

    @Override
    public void setTrainer(Trainer trainer) {
        horse.setTrainer(trainer);
    }

    @Override
    public Owner getOwner() {
        return horse.getOwner();
    }

    @Override
    public void setOwner(Owner owner) {
        horse.setOwner(owner);
    }

    @Override
    public Date getBirthday() {
        return horse.getBirthday();
    }

    @Override
    public void setBirthday(Date birthday) {
        horse.setBirthday(birthday);
    }

    @Override
    public Gender getGender() {
        return horse.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        horse.setGender(gender);
    }

    @Override
    public Horse getSir() {
        return horse.getSir();
    }

    @Override
    public void setSir(Horse sir) {
        horse.setSir(sir);
    }

    @Override
    public Horse getDam() {
        return horse.getDam();
    }

    @Override
    public void setDam(Horse dam) {
        horse.setDam(dam);
    }
}
