package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.entities.HorseEntity;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * Simplify creating HorseEntity instance using builder pattern.
 *
 * @see HorseEntity
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
    private Horse sir;
    private Horse dam;

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

    public HorseBuilder setSir(Horse sir) {
        this.sir = sir;
        return this;
    }

    public HorseBuilder setSirById(int id) {
        this.sir = new HorseEntity();
        this.sir.setId(id);
        return this;
    }

    public HorseBuilder setDam(Horse dam) {
        this.dam = dam;
        return this;
    }

    public HorseBuilder setDamById(int id) {
        this.dam = new HorseEntity();
        this.dam.setId(id);
        return this;
    }

    public Horse build() {
        Horse horse = new HorseEntity();

        horse.setId(id);
        horse.setName(name);
        horse.setTrainer(trainer);
        horse.setOwner(owner);
        horse.setBirthday(birthday);
        horse.setGender(gender);
        horse.setSir(sir);
        horse.setDam(dam);

        return horse;
    }
}