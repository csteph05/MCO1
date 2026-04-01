package model;

/**
 * this class represents a single entry on the leaderboard
 * it stores the player's name and their final savings
 * at the end of the season
 */
public class HighScore {
    private String playerName;
    private int savings;

    /**
     * This is the constructor that creates an instance of the model.HighScore class when called.
     *
     * @param playerName is the name of the player who achieved the score.
     * @param savings is the final amount of money the player earned at the end of the season.
     */

    public HighScore(String playerName, int savings){
        this.playerName = playerName;
        this.savings = savings;
    }

    /**
     * Returns the name of the player for the highscore entry.
     *
     * @return the playerName
     */

    public String getPlayerName(){
        return playerName;
    }

    /**
     * Returns the final savings.
     *
     * @return the savings
     */

    public int getSavings(){
        return savings;
    }

}
