package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Horse;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * Simplify creating Horse instance using builder pattern.
 *
 * @see Horse
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public class HorseBuilder {
    private long id;
    private String name;
    private Trainer trainer;
    private Owner owner;
    private Date birthday;
    private Gender gender;

    public HorseBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public HorseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public HorseBuilder setTrainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public HorseBuilder setTrainerById(int id) {
        this.trainer = new Trainer();
        this.trainer.setId(id);
        return this;
    }

    public HorseBuilder setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public HorseBuilder setOwnerById(int id) {
        this.owner = new Owner();
        this.owner.setId(id);
        return this;
    }

    public HorseBuilder setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public HorseBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public HorseBuilder setGender(String gender) {
        this.gender = Gender.getGender(gender);
        return this;
    }


    public Horse build() {
        Horse horse = new Horse();

        horse.setId(id);
        horse.setName(name);
        horse.setTrainer(trainer);
        horse.setOwner(owner);
        horse.setBirthday(birthday);
        horse.setGender(gender);

        return horse;
    }
}