package com.room414.racingbets.web.model.viewmodels;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class ParticipantForm implements Serializable {
    private static final long serialVersionUID = -541650038039106907L;

    private long id;
    private int number;
    private long horse;
    private float carriedWeight;
    private int topSpeed;
    private int officialRating;
    private double odds;
    private long jockey;
    private long trainer;
    private int place;

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

    public long getHorse() {
        return horse;
    }

    public void setHorse(long horse) {
        this.horse = horse;
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

    public long getJockey() {
        return jockey;
    }

    public void setJockey(long jockey) {
        this.jockey = jockey;
    }

    public long getTrainer() {
        return trainer;
    }

    public void setTrainer(long trainer) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipantForm that = (ParticipantForm) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (horse != that.horse) return false;
        if (Float.compare(that.carriedWeight, carriedWeight) != 0) return false;
        if (topSpeed != that.topSpeed) return false;
        if (officialRating != that.officialRating) return false;
        if (Double.compare(that.odds, odds) != 0) return false;
        if (jockey != that.jockey) return false;
        if (trainer != that.trainer) return false;
        return place == that.place;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + number;
        result = 31 * result + (int) (horse ^ (horse >>> 32));
        result = 31 * result + (carriedWeight != +0.0f ? Float.floatToIntBits(carriedWeight) : 0);
        result = 31 * result + topSpeed;
        result = 31 * result + officialRating;
        temp = Double.doubleToLongBits(odds);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (jockey ^ (jockey >>> 32));
        result = 31 * result + (int) (trainer ^ (trainer >>> 32));
        result = 31 * result + place;
        return result;
    }

    @Override
    public String toString() {
        return "ParticipantForm{" +
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
