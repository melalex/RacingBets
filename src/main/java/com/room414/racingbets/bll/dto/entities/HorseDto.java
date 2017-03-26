package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.dal.domain.enums.Gender;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for Horse class
 *
 * @see com.room414.racingbets.dal.domain.entities.Horse
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class HorseDto implements Serializable {
    private static final long serialVersionUID = -4741079009638927620L;

    private long id;
    private String name;
    private TrainerDto trainer;
    private OwnerDto owner;
    private Date birthday;
    private Gender gender;

    public HorseDto() {
    }

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

    public TrainerDto getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDto trainer) {
        this.trainer = trainer;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorseDto horseDto = (HorseDto) o;

        if (id != horseDto.id) {
            return false;
        }

        if (name != null ? !name.equals(horseDto.name) : horseDto.name != null) {
            return false;
        }

        if (trainer != null ? !trainer.equals(horseDto.trainer) : horseDto.trainer != null) {
            return false;
        }

        if (owner != null ? !owner.equals(horseDto.owner) : horseDto.owner != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(horseDto.birthday) : horseDto.birthday != null) {
            return false;
        }

        if (gender != horseDto.gender) {
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

        return result;
    }

    @Override
    public String toString() {
        return "HorseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainer=" + trainer +
                ", owner=" + owner +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }
}
