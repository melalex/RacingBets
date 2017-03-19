package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
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
public interface BetDao extends CrudDao<Long, Bet> {
    /**
     * @return bets made by the user with id == id param
     */
    List<Bet> findByUserId(long id, long offset, long limit);

    /**
     * @return number of bets made by the user with id == id param
     */
    long findByUserIdCount(long id);

    /**
     * @return bets make on RaceDto with id == id param
     */
    List<Bet> findByRaceId(long id, long offset, long limit);

    /**
     * @return number of bets make on RaceDto with id == id param
     */
    long findByRaceIdCount(long id);

    /**
     * Update all bets in the list.
     *
     * @return number of updates
     */
    long update(List<Bet> bets);

    /**
     * @return OddsDto of concrete BetDto.
     * @see <a href="https://en.wikipedia.org/wiki/Parimutuel_betting#Example">
     *          OddsDto calculation example
     *      </a>
     */
    Odds getOdds(Bet bet);
}
