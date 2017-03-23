package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.base.BaseRace;
import com.room414.racingbets.dal.domain.builders.RaceParticipantThumbnailBuilder;


/**
 * Class that represent thumbnail of participant.
 * To create instances of RaceParticipantThumbnail is recommended to use the RaceParticipantThumbnailBuilder.
 *
 * @see RaceParticipantThumbnailBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RaceParticipantThumbnail extends BaseRace {
    private static final long serialVersionUID = 2315996788389295233L;

    private Participant participant;

    public static RaceParticipantThumbnailBuilder builder() {
        return new RaceParticipantThumbnailBuilder();
    }

    public RaceParticipantThumbnail() {

    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        RaceParticipantThumbnail that = (RaceParticipantThumbnail) o;

        return participant != null ? participant.equals(that.participant) : that.participant == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 31 * result + (participant != null ? participant.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "RaceParticipantThumbnail{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", racecourse=" + getRacecourse() +
                ", start=" + getStart() +
                ", minBet=" + getMinBet() +
                ", commission=" + getCommission() +
                ", trackCondition=" + getTrackCondition() +
                ", raceType=" + getRaceType() +
                ", raceStatus=" + getRaceStatus() +
                ", raceClass=" + getRaceClass() +
                ", minAge=" + getMinAge() +
                ", minRating=" + getMinRating() +
                ", maxRating=" + getMaxRating() +
                ", distance=" + getDistance() +
                "participant=" + participant +
                '}';
    }
}
