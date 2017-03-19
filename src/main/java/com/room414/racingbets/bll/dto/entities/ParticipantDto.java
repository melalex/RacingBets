package com.room414.racingbets.bll.dto.entities;


import java.io.Serializable;

/**
 * DTO for Participant class
 *
 * @see com.room414.racingbets.dal.domain.entities.Participant
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class ParticipantDto implements Serializable {
    private static final long serialVersionUID = -2072016846596822050L;

    private long id;
    private int number;
    private HorseDto horse;
    private float carriedWeight;
    private int topSpeed;
    private int officialRating;
    private double odds;
    private JockeyDto jockey;
    private TrainerDto trainer;
    private int place;

    public ParticipantDto() {
    }

    public ParticipantDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public HorseDto getHorse() {
        return horse;
    }

    public void setHorse(HorseDto horseEntity) {
        this.horse = horseEntity;
    }

    public float getCarriedWeight() {
        return carriedWeight;
    }

    public void setCarriedWeight(float carriedWeight) {
        this.carriedWeight = carriedWeight;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }

    public int getOfficialRating() {
        return officialRating;
    }

    public void setOfficialRating(int officialRating) {
        this.officialRating = officialRating;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public JockeyDto getJockey() {
        return jockey;
    }

    public void setJockey(JockeyDto jockey) {
        this.jockey = jockey;
    }

    public TrainerDto getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDto trainer) {
        this.trainer = trainer;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipantDto that = (ParticipantDto) o;

        if (id != that.id) {
            return false;
        }

        if (number != that.number) {
            return false;
        }

        if (Float.compare(that.carriedWeight, carriedWeight) != 0) {
            return false;
        }

        if (topSpeed != that.topSpeed) {
            return false;
        }

        if (officialRating != that.officialRating) {
            return false;
        }

        if (Double.compare(that.odds, odds) != 0) {
            return false;
        }

        if (place != that.place) {
            return false;
        }

        if (horse != null ? !horse.equals(that.horse) : that.horse != null) {
            return false;
        }

        if (jockey != null ? !jockey.equals(that.jockey) : that.jockey != null) {
            return false;
        }

        if (trainer != null ? !trainer.equals(that.trainer) : that.trainer != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        result = (int) (id ^ (id >>> 32));
        result = 31 * result + number;
        result = 31 * result + (horse != null ? horse.hashCode() : 0);
        result = 31 * result + (carriedWeight != +0.0f ? Float.floatToIntBits(carriedWeight) : 0);
        result = 31 * result + topSpeed;
        result = 31 * result + officialRating;

        temp = Double.doubleToLongBits(odds);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        result = 31 * result + (jockey != null ? jockey.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        result = 31 * result + place;

        return result;
    }

    @Override
    public String toString() {
        return "ParticipantDto{" +
                "id=" + id +
                ", number=" + number +
                ", horse=" + horse +
                ", carriedWeight=" + carriedWeight +
                ", topSpeed=" + topSpeed +
                ", officialRating=" + officialRating +
                ", odds=" + odds +
                ", jockey=" + jockey +
                ", trainer=" + trainer +
                ", place=" + place +
                '}';
    }
}
