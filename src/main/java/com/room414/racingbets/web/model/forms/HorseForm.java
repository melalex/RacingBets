package com.room414.racingbets.web.model.forms;

import com.room414.racingbets.dal.domain.enums.Gender;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class HorseForm implements Serializable {
    private static final long serialVersionUID = 4400216055448025715L;

    private long id;
    private String name;
    private long trainer;
    private long owner;
    private Date birthday;
    private Gender gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTrainer() {
        return trainer;
    }

    public void setTrainer(long trainer) {
        this.trainer = trainer;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HorseForm horseForm = (HorseForm) o;

        if (id != horseForm.id) return false;
        if (trainer != horseForm.trainer) return false;
        if (owner != horseForm.owner) return false;
        if (name != null ? !name.equals(horseForm.name) : horseForm.name != null) return false;
        if (birthday != null ? !birthday.equals(horseForm.birthday) : horseForm.birthday != null) return false;

        return gender == horseForm.gender;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (trainer ^ (trainer >>> 32));
        result = 31 * result + (int) (owner ^ (owner >>> 32));
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "HorseForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainer=" + trainer +
                ", owner=" + owner +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }
}
