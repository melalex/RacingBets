package com.room414.racingbets.dal.domain.entities;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
// TODO: add comments
public class Participant {
    private int id;
    private int number;
    private Horse horse;
    private Race race;
    private float carriedWeight;
    // TODO: change SQL representation
    private float topSpeed;
    // TODO: delete this field
    private int racingPostRaiting;
    // TODO:  maybe better move this field to horse
    private int oficialRating;
    private int oddsNumerator;
    private int oddsDenominator;
    private HeadGear headGear;
    private Jockey jockey;
    private Trainer trainer;
    private int place;
    // TODO: delete this field;
    private int winner;

    // TODO: fix db representation
    // TODO: valueOf
    public enum HeadGear {
        BLINKERS,
        VISOR,
        EYESHIELD,
        HOOD,
        TONGUE_STRAP,
        SHEEPSKIN_CHEEKPIECES
    }
}
