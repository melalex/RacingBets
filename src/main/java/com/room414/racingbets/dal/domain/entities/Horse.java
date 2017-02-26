package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.enums.Gender;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Horse implements Serializable {
    private static final long serialVersionUID = -4741079009638927620L;

    private int id;
    private String name;
    private Trainer trainer;
    private Owner owner;
    private Date birthday;
    private Gender gender;
    private Horse sir;
    private Horse dam;

    public Horse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Horse getSir() {
        return sir;
    }

    public void setSir(Horse sir) {
        this.sir = sir;
    }

    public Horse getDam() {
        return dam;
    }

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

        Horse horse = (Horse) o;

        if (id != horse.id) {
            return false;
        }

        if (name != null ? !name.equals(horse.name) : horse.name != null) {
            return false;
        }

        if (trainer != null ? !trainer.equals(horse.trainer) : horse.trainer != null) {
            return false;
        }

        if (owner != null ? !owner.equals(horse.owner) : horse.owner != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(horse.birthday) : horse.birthday != null) {
            return false;
        }

        if (gender != horse.gender) {
            return false;
        }

        if (sir != null ? !sir.equals(horse.sir) : horse.sir != null) {
            return false;
        }

        if (dam != null ? !dam.equals(horse.dam) : horse.dam != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

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
        return "Horse{" +
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
