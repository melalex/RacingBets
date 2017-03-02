package com.room414.racingbets.dal.abstraction.infrastructure;

import java.io.Serializable;

/**
 * Tuple with two elements
 *
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public class Pair<F, S> implements Serializable {
    private static final long serialVersionUID = -2864092039486330708L;

    private F firstElement;
    private S secondElement;

    public Pair() {
    }

    public Pair(F firstElement, S secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public F getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(F firstElement) {
        this.firstElement = firstElement;
    }

    public S getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(S secondElement) {
        this.secondElement = secondElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (firstElement != null ? !firstElement.equals(pair.firstElement) : pair.firstElement != null) {
            return false;
        }

        if (secondElement != null ? !secondElement.equals(pair.secondElement) : pair.secondElement != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstElement != null ? firstElement.hashCode() : 0;

        result = 31 * result + (secondElement != null ? secondElement.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "firstElement=" + firstElement +
                ", secondElement=" + secondElement +
                '}';
    }
}
