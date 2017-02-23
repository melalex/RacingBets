package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Class that represents track surface condition type.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - track surface condition id</li>
 *      <li>conditionType - track surface condition type</li>
 * </ul>
 *
 * @link https://en.wikipedia.org/wiki/Going_(horse_racing)#United_Kingdom_and_Ireland
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class TrackCondition implements Serializable {
    private int id;
    private String conditionType;

    public TrackCondition() {

    }

    public TrackCondition(int id, String conditionType) {
        this.id = id;
        this.conditionType = conditionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrackCondition that = (TrackCondition) o;

        if (id != that.id) {
            return false;
        }

        if (conditionType != null ? !conditionType.equals(that.conditionType) : that.conditionType != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (conditionType != null ? conditionType.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "TrackCondition{" +
                "id=" + id +
                ", conditionType='" + conditionType + '\'' +
                '}';
    }
}
