package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * DTO for Horse class
 *
 * @see Horse
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class HorseDto {
    private static final long serialVersionUID = -4741079009638927620L;

    private long id;
    private String name;
    private TrainerDto trainerDto;
    private OwnerDto ownerDto;
    private Date birthday;
    private Gender gender;
    private Horse sir;
    private Horse dam;

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

    public TrainerDto getTrainerDto() {
        return trainerDto;
    }

    public void setTrainerDto(TrainerDto trainerDto) {
        this.trainerDto = trainerDto;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
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

        HorseDto horseDto = (HorseDto) o;

        if (id != horseDto.id) {
            return false;
        }

        if (name != null ? !name.equals(horseDto.name) : horseDto.name != null) {
            return false;
        }

        if (trainerDto != null ? !trainerDto.equals(horseDto.trainerDto) : horseDto.trainerDto != null) {
            return false;
        }

        if (ownerDto != null ? !ownerDto.equals(horseDto.ownerDto) : horseDto.ownerDto != null) {
            return false;
        }

        if (birthday != null ? !birthday.equals(horseDto.birthday) : horseDto.birthday != null) {
            return false;
        }

        if (gender != horseDto.gender) {
            return false;
        }

        if (sir != null ? !sir.equals(horseDto.sir) : horseDto.sir != null) {
            return false;
        }

        if (dam != null ? !dam.equals(horseDto.dam) : horseDto.dam != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (trainerDto != null ? trainerDto.hashCode() : 0);
        result = 31 * result + (ownerDto != null ? ownerDto.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (sir != null ? sir.hashCode() : 0);
        result = 31 * result + (dam != null ? dam.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "HorseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainerDto=" + trainerDto +
                ", ownerDto=" + ownerDto +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", sir=" + sir +
                ", dam=" + dam +
                '}';
    }
}
