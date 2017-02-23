package com.room414.racingbets.dal.domain.entities;

/**
 * Class that represents track surface condition.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - track surface condition id</li>
 *      <li>name - track surface condition type</li>
 * </ul>
 *
 * @link https://en.wikipedia.org/wiki/Going_(horse_racing)#United_Kingdom_and_Ireland
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class TrackCondition {
    private int id;
    private String name;

    public TrackCondition() {

    }

    public TrackCondition(int id, String name) {
        this.id = id;
        this.name = name;
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

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);

        return result;
    }
}
