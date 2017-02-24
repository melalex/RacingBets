package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class that represents a bet of handicapper.
 *
 * @link http://www.dummies.com/games/casino-games/betting-on-horse-racing-for-dummies-cheat-sheet/
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Bet implements Serializable {
    private static final long serialVersionUID = -5253938704091728335L;

    private int id;
    private ApplicationUser user;
    private BigDecimal betSize;
    private BetType betType;
    private List<Participant> participants;

    // TODO: valueOf
    public enum BetType {
        /**
         * Your horse must finish 1st, 2nd, or 3rd; modest payoffs
         */
        SHOW {
            @Override
            public String toString() {
                return "Show";
            }
        },
        /**
         * Your horse must finish 1st or 2nd; payoffs better than to show
         */
        PLACE {
            @Override
            public String toString() {
                return "Place";
            }
        },
        /**
         * Your horse must finish 1st; payoff determined by the win odds
         */
        WIN {
            @Override
            public String toString() {
                return "Win";
            }
        },
        /**
         * Your horses must finish 1st and 2nd in either order; a normal play is to box three horses
         */
        QUINELLA {
            @Override
            public String toString() {
                return "Quinella";
            }
        },
        /**
         * Your horses must finish 1st and 2nd in exact order; riskier bet
         * that can pay a little or a lot, depending on the horses’ odds
         */
        EXACTA {
            @Override
            public String toString() {
                return "Exacta";
            }
        },
        /**
         * Your horses must finish 1st, 2nd, and 3rd in exact order; can
         * be expensive to play if you use a lot of horses
         */
        TRIFECTA {
            @Override
            public String toString() {
                return "Trifecta";
            }
        },
        /**
         * Your horses must finish 1st, 2nd, 3rd, and 4th; hard to bet
         * unless you have a sizeable bankroll; big payoff possible
         */
        SUPERFECTA {
            @Override
            public String toString() {
                return "Superfecta";
            }
        },
        /**
         * Your horses must win the two consecutive races; chance for a
         * nice payoff with mid-priced horses
         */
        DAILY_DOUBLE {
            @Override
            public String toString() {
                return "Daily Double";
            }
        },
        /**
         * Your horses must win three consecutive races; it’s a
         * daily double plus another race; $1 unit makes it affordable
         */
        PICK_3 {
            @Override
            public String toString() {
                return "Pick 3";
            }
        },
        /**
         * Your horses must win four consecutive races; chance for a big
         * score for a modest amount
         */
        PICK_4 {
            @Override
            public String toString() {
                return "Pick 4";
            }
        },
        /**
         * Your horses must win six consecutive races; very expensive to
         * play; huge payoffs possible; a home run bet
         */
        PICK_6 {
            @Override
            public String toString() {
                return "Pick 6";
            }
        },
    }

    public Bet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public BigDecimal getBetSize() {
        return betSize;
    }

    public void setBetSize(BigDecimal betSize) {
        this.betSize = betSize;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public List<Participant> getParticipants() {
        return participants.subList(0, participants.size() - 1);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants.subList(0, participants.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bet bet = (Bet) o;

        if (id != bet.id) {
            return false;
        }

        if (user != null ? !user.equals(bet.user) : bet.user != null) {
            return false;
        }

        if (betSize != null ? !betSize.equals(bet.betSize) : bet.betSize != null) {
            return false;
        }

        if (betType != bet.betType) {
            return false;
        }

        if (participants != null ? !participants.equals(bet.participants) : bet.participants != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (betSize != null ? betSize.hashCode() : 0);
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", user=" + user +
                ", betSize=" + betSize +
                ", betType=" + betType +
                ", participants=" + participants +
                '}';
    }
}
