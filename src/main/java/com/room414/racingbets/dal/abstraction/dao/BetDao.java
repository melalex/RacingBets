package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.enums.BetStatus;

import java.util.List;

/**
 * DAO for Bet entity
 *
 * @see Bet
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface BetDao extends CrudDao<Integer, Bet> {
    /**
     * @return bets made by the user with id == id param
     */
    List<Bet> findByUserId(int id, int offset, int limit);

    /**
     * @return number of bets made by the user with id == id param
     */
    int findByUserIdCount(int id);

    /**
     * @return bets make on Race with id == id param
     */
    List<Bet> findByRaceId(int id, int offset, int limit);

    /**
     * @return number of bets make on Race with id == id param
     */
    int findByRaceIdCount(int id);

    /**
     * Sets bet status to status param
     */
    void updateStatus(int id, BetStatus status);

    /**
     * Updates bet status (WIN, LOSE) whichever it win or not
     *
     * @param places Participants id in the order in which they finished
     */
    void updateStatus(int places);

    /**
     * @return Odds of concrete Bet.
     * @see <a href="https://en.wikipedia.org/wiki/Parimutuel_betting#Example">
     *          Odds calculation example
     *      </a>
     */
    Odds getOdds(Bet bet);
}
