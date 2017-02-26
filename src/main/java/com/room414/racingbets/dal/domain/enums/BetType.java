package com.room414.racingbets.dal.domain.enums;

/**
 * Enum that represents type of bet.
 *
 * @see <a href="http://www.dummies.com/games/casino-games/betting-on-horse-racing-for-dummies-cheat-sheet/">
 *          Horse race beting
 *      </a>
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public enum BetType {
    /**
     * Your horse must finish 1st, 2nd, or 3rd; modest payoffs
     */
    SHOW("Show"),
    /**
     * Your horse must finish 1st or 2nd; payoffs better than to show
     */
    PLACE("Place"),
    /**
     * Your horse must finish 1st; payoff determined by the win odds
     */
    WIN("Win"),
    /**
     * Your horses must finish 1st and 2nd in either order; a normal play is to box three horses
     */
    QUINELLA("Quinella"),
    /**
     * Your horses must finish 1st and 2nd in exact order; riskier bet
     * that can pay a little or a lot, depending on the horses’ odds
     */
    EXACTA("Exacta"),
    /**
     * Your horses must finish 1st, 2nd, and 3rd in exact order; can
     * be expensive to play if you use a lot of horses
     */
    TRIFECTA("Trifecta"),
    /**
     * Your horses must finish 1st, 2nd, 3rd, and 4th; hard to bet
     * unless you have a sizeable bankroll; big payoff possible
     */
    SUPERFECTA("Superfecta"),
    /**
     * Your horses must win the two consecutive races; chance for a
     * nice payoff with mid-priced horses
     */
    DAILY_DOUBLE("Daily Double"),
    /**
     * Your horses must win three consecutive races; it’s a
     * daily double plus another race; $1 unit makes it affordable
     */
    PICK_3("Pick 3"),
    /**
     * Your horses must win four consecutive races; chance for a big
     * score for a modest amount
     */
    PICK_4("Pick 4"),
    /**
     * Your horses must win six consecutive races; very expensive to
     * play; huge payoffs possible; a home run bet
     */
    PICK_6("Pick 6");

    private String name;

    BetType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BetType getBetType(String name) {
        for(BetType v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no BetType named " + name);
    }
}
