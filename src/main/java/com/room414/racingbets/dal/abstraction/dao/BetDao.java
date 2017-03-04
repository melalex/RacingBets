package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
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
public interface BetDao extends CrudDao<Long, Bet> {
    /**
     * @return bets made by the user with id == id param
     */
    List<Bet> findByUserId(long id, long offset, long limit) throws DalException;

    /**
     * @return number of bets made by the user with id == id param
     */
    long findByUserIdCount(long id) throws DalException;

    /**
     * @return bets make on Race with id == id param
     */
    List<Bet> findByRaceId(long id, long offset, long limit) throws DalException;

    /**
     * @return number of bets make on Race with id == id param
     */
    long findByRaceIdCount(long id) throws DalException;

    /**
     * Update all bets in the list.
     *
     * @return number of updates
     */
    long update(List<Bet> bets);

    /**
     * @return Odds of concrete Bet.
     * @see <a href="https://en.wikipedia.org/wiki/Parimutuel_betting#Example">
     *          Odds calculation example
     *      </a>
     */
    Odds getOdds(Bet bet);
}
