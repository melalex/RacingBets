package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * Class that represents HorseEntity entity.
 * To createUnitOfWorkFactory instances of HorseEntity is recommended to use the HorseBuilder.
 *
 * @see com.room414.racingbets.dal.domain.builders.HorseBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class HorseEntity extends Horse {
    private static final long serialVersionUID = -4741079009638927620L;

    private long id;
    private String name;
    private Trainer trainer;
    private Owner owner;
    private Date birthday;
    private Gender gender;
    private Horse sir;
    private Horse dam;

    public HorseEntity() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Trainer getTrainer() {
        return trainer;
    }

    @Override
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Horse getSir() {
        return sir;
    }

    @Override
    public void setSir(Horse sir) {
        this.sir = sir;
    }

    @Override
    public Horse getDam() {
        return dam;
    }

    @Override
    public void setDam(Horse dam) {
        this.dam = dam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorseEntity horseEntity = (HorseEntity) o;

        if (id != horseEntity.id) {
            return false;
        }

        if (name != null ? !name.equals(horseEntity.name) : horseEntity.name != null) {
            return false;
        }

        if (trainer != null ? !trainer.equals(horseEntity.trainer) : horseEntity.trainer != null) {
            return false;
        }

        if (owner != null ? !owner.equals(horseEntity.owner) : horseEntity.owner != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(horseEntity.birthday) : horseEntity.birthday != null) {
            return false;
        }

        if (gender != horseEntity.gender) {
            return false;
        }

        if (sir != null ? !sir.equals(horseEntity.sir) : horseEntity.sir != null) {
            return false;
        }

        if (dam != null ? !dam.equals(horseEntity.dam) : horseEntity.dam != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (sir != null ? sir.hashCode() : 0);
        result = 31 * result + (dam != null ? dam.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "HorseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainer=" + trainer +
                ", owner=" + owner +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", sir=" + sir +
                ", dam=" + dam +
                '}';
    }
}
