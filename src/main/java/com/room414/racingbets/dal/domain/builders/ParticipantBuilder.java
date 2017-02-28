package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.entities.HorseEntity;
import com.room414.racingbets.dal.domain.entities.Jockey;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Trainer;

/**
 * Simplify creating Participant instance using builder pattern.
 *
 * @see Participant
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class ParticipantBuilder {
    private int id;
    private int number;
    private Horse horse;
    private float carriedWeight;
    private int topSpeed;
    private int officialRating;
    private int oddsNumerator;
    private int oddsDenominator;
    private Jockey jockey;
    private Trainer trainer;
    private int place;

    public ParticipantBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ParticipantBuilder setNumber(int number) {
        this.number = number;
        return this;
    }

    public ParticipantBuilder setHorse(Horse horse) {
        this.horse = horse;
        return this;
    }

    public ParticipantBuilder setHorseById(int id) {
        this.horse = new HorseEntity();
        this.horse.setId(id);
        return this;
    }

    public ParticipantBuilder setCarriedWeight(float carriedWeight) {
        this.carriedWeight = carriedWeight;
        return this;
    }

    public ParticipantBuilder setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
        return this;
    }

    public ParticipantBuilder setOfficialRating(int officialRating) {
        this.officialRating = officialRating;
        return this;
    }

    public ParticipantBuilder setOddsNumerator(int oddsNumerator) {
        this.oddsNumerator = oddsNumerator;
        return this;
    }

    public ParticipantBuilder setOddsDenominator(int oddsDenominator) {
        this.oddsDenominator = oddsDenominator;
        return this;
    }

    public ParticipantBuilder setJockey(Jockey jockey) {
        this.jockey = jockey;
        return this;
    }

    public ParticipantBuilder setJockeyById(int id) {
        this.jockey = new Jockey();
        this.jockey.setId(id);
        return this;
    }

    public ParticipantBuilder setTrainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public ParticipantBuilder setTrainerById(int id) {
        this.trainer = new Trainer();
        this.trainer.setId(id);
        return this;
    }

    public ParticipantBuilder setPlace(int place) {
        this.place = place;
        return this;
    }

    public Participant createParticipant() {
        Participant participant = new Participant();

        participant.setId(id);
        participant.setNumber(number);
        participant.setHorse(horse);
        participant.setCarriedWeight(carriedWeight);
        participant.setTopSpeed(topSpeed);
        participant.setOfficialRating(officialRating);
        participant.setOddsNumerator(oddsNumerator);
        participant.setOddsDenominator(oddsDenominator);
        participant.setJockey(jockey);
        participant.setTrainer(trainer);
        participant.setPlace(place);

        return participant;
    }
}