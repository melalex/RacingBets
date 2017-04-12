package com.room414.racingbets.bll.abstraction.infrastructure;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Apr 2017
 */
public class BetError implements Serializable {
    private static final long serialVersionUID = 2924046385243151315L;

    private String field;
    private BetErrorType errorType;

    public enum BetErrorType {
        INVALID_BET_TYPE,
        UNCONFIRMED_EMAIL,
        NOT_ENOUGH_MONEY,
        INVALID_RACE_STATUS,
        BET_IS_TOO_SMALL
    }

    public BetError() {
    }

    public BetError(String field, BetErrorType errorType) {
        this.field = field;
        this.errorType = errorType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public BetErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(BetErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BetError betError = (BetError) o;

        if (field != null ? !field.equals(betError.field) : betError.field != null) return false;
        return errorType == betError.errorType;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (errorType != null ? errorType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BetError{" +
                "field='" + field + '\'' +
                ", errorType=" + errorType +
                '}';
    }
}
