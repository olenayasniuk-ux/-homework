package com.shpp.p2p.cs.oyasniuk.assignment3;

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

/**
 * Assignment3Part5 simulates a coin-flipping gambling game (based on the St. Petersburg paradox).
 * A lucky person plays rounds where a coin is tossed repeatedly. Every time "heads" comes up,
 * the money on the table doubles. The round ends when "tails" comes up, and the player keeps
 * the accumulated money. The game continues in multiple rounds until the player's total
 * bankroll reaches or exceeds $20.
 */
public class Assignment3Part5 extends TextProgram {
    /**
     * Format string template to display the amount of money earned in the current round.
     */
    public static final String TEXT_EARN_MONEY = "This game, you earned $%d";
    /**
     * Format string template to display the total accumulated money across all rounds.
     */
    public static final String TEXT_TOTAL_SUM = "Your total is $%d";
    /**
     * Format string template to display the total number of rounds played to reach the target.
     */
    public static final String TEXT_NUMBER_GAMES = "It took %d games to earn $20";


    public void run() {
        int moneyOnTable = 0;
        int luckyPersonMoney = 0;
        int numberGames = 0;

        while (luckyPersonMoney < 20) {
            moneyOnTable++;
            // Loop runs as long as the coin lands on heads, doubling the current pot
            while (tossCoin()) {
                moneyOnTable += moneyOnTable;
            }
            luckyPersonMoney += moneyOnTable;
            System.out.printf(TEXT_EARN_MONEY + "\n", moneyOnTable);
            System.out.printf(TEXT_TOTAL_SUM + "\n", luckyPersonMoney);
            moneyOnTable = 0;
            numberGames++;
        }
        System.out.printf(TEXT_NUMBER_GAMES, numberGames);
    }

    /**
     * Simulates a single random coin toss with a 50/50 probability.
     *
     * @return true if the coin lands on "heads" (game continues),
     * false if it lands on "tails" (round ends).
     */
    public boolean tossCoin() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        return rgen.nextBoolean();
    }
}
