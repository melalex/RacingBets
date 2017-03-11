package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Alexander Melashchenko
 * @version 1.0 01 Mar 2017
 */
public class Odds implements Serializable {
    private static final long serialVersionUID = -8512671896597348845L;
    private static final int SCALING = 4;
    
    private BigDecimal prizePool;
    private BigDecimal eventPool;
    private double commission;

    public Odds() {
    }

    public Odds(BigDecimal prizePool, BigDecimal eventPool, double commission) {
        this.prizePool = prizePool;
        this.eventPool = eventPool;
        this.commission = commission;
    }

    public Odds(double prizePool, double eventPool, double commission) {
        this(BigDecimal.valueOf(prizePool), BigDecimal.valueOf(eventPool), commission);
    }

    public BigDecimal getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(BigDecimal prizePool) {
        this.prizePool = prizePool;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public BigDecimal getEventPool() {
        return eventPool;
    }

    public void setEventPool(BigDecimal eventPool) {
        this.eventPool = eventPool;
    }

    public double getDoubleOdds() {
        return getDecimalOdds().doubleValue();
    }

    public BigDecimal getDecimalOdds() {
        BigDecimal bookmakerPart = prizePool.multiply(new BigDecimal(commission));
        BigDecimal amount = bookmakerPart.subtract(bookmakerPart);

        return amount.divide(eventPool, SCALING, BigDecimal.ROUND_DOWN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Odds odds = (Odds) o;

        if (Double.compare(odds.commission, commission) != 0) {
            return false;
        }

        if (prizePool != null ? prizePool.compareTo(odds.prizePool) != 0 : odds.prizePool != null) {
            return false;
        }

        if (eventPool != null ? eventPool.compareTo(odds.eventPool) != 0 : odds.eventPool != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        temp = prizePool != null ? Double.doubleToLongBits(prizePool.doubleValue()) : 0;
        result = (int) (temp ^ (temp >>> 32));

        temp = prizePool != null ? Double.doubleToLongBits(eventPool.doubleValue()) : 0;
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(commission);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public String toString() {
        return "Odds{" +
                "prizePool=" + prizePool +
                ", eventPool=" + eventPool +
                ", commission=" + commission +
                '}';
    }
}
