package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;

/**
 * Class that represents weather type.
 *
 * <h4>Fields</h4>
 * <ul>
 *      <li>id - weather type id</li>
 *      <li>conditionType - weather type name</li>
 * </ul>
 *
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Weather implements Serializable {
    private int id;
    private String weatherType;

    public Weather() {

    }

    public Weather(int id, String weatherType) {
        this.id = id;
        this.weatherType = weatherType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Weather weather = (Weather) o;

        if (id != weather.id) {
            return false;
        }

        if (weatherType != null ? !weatherType.equals(weather.weatherType) : weather.weatherType != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (weatherType != null ? weatherType.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", weatherType='" + weatherType + '\'' +
                '}';
    }
}
