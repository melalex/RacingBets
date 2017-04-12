package com.room414.racingbets.web.model.viewmodels;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Apr 2017
 */
public class MakeBetResponse implements Serializable {
    private static final long serialVersionUID = 4883922082802180264L;

    private String message;
    private BigDecimal balance;

    public MakeBetResponse() {
    }

    public MakeBetResponse(String message, BigDecimal balance) {
        this.message = message;
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MakeBetResponse that = (MakeBetResponse) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MakeBetResponse{" +
                "message='" + message + '\'' +
                ", balance=" + balance +
                '}';
    }
}
