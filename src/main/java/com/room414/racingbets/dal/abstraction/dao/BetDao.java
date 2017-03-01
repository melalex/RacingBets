package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import java.util.List;

/**
 * DAO for Bet entity
 *
 * @see Bet
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
// TODO: change status
public interface BetDao extends CrudDao<Integer, Bet> {
    /**
     * @return bets made by the user with id == id param
     */
    List<Bet> findByUserId(int id);

    /**
     * @return number of bets made by the user with id == id param
     */
    int findByUserIdCount(int id);

    /**
     * @return bets make on Race with id == id param
     */
    List<Bet> findByRaceId(int id);

    /**
     * @return number of bets make on Race with id == id param
     */
    int findByRaceIdCount(int id);

    /**
     * @return Odds of concrete Bet.
     * @see <a href="https://en.wikipedia.org/wiki/Parimutuel_betting#Example">
     *          Odds calculation example
     *      </a>
     */
    Odds getOdds(Bet bet);
}
