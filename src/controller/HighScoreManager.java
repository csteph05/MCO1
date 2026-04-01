package controller;

import java.util.*;
import model.*;

/**
 * this class handles the leaderboard system.
 * it handles a list of top 10 highest-earning players, sorting them
 * by their final savings and checking if new players can make the board
 *
 */
public class HighScoreManager {
    private static final int MAX_ENTRIES = 10;
    private List<HighScore> scores;

    /**
     * Constructor that initializes an empty high score list.
     */
    public HighScoreManager(){
        this.scores = new ArrayList<>();
    }

    /**
     * Attempts to add a new score to the leaderboard.
     * Only added if list has less than 10 entries or 
     * score is higher than the lowest entry.
     *
     * @param playerName the name of the player
     * @param savings the final savings at the end of the game
     * @return true if added, false if their score was too
     */
    public boolean addScore(String playerName, int savings){
        HighScore newScore = new HighScore(playerName, savings);
        // if less than 10 then just add the score
        if(scores.size() < MAX_ENTRIES){
            scores.add(newScore);
            sortScores();
            return true;
        }

        // if full then check last place
        HighScore lowest = scores.get(scores.size() - 1);
        // if new score is better than the lowest
        if(savings > lowest.getSavings()){
            // remove the lowest guy
            scores.remove(lowest);
            // add the new guy in
            scores.add(newScore);
            // re rank the highscores
            sortScores();
            return true;
        }
        return false;
    }

    /**
     * Rearranges the list so the highest money is at the top
     *
     *
     */
    private void sortScores(){
        scores.sort((a, b) -> b.getSavings() - a.getSavings());
    }

    /**
     * Displays the high score table.
     */
    public void displayScores(){
        System.out.println("\n==========================================");
        System.out.println("           HIGH SCORE TABLE              ");
        System.out.println("==========================================");
        // if nobody has played yet
        if(scores.isEmpty()){
            System.out.println("No scores yet!");
        } else {
            // print the rank, the name, and the score for every person in the list
            for(int i = 0; i < scores.size(); i++){
                System.out.println((i+1) + ". " + scores.get(i).getPlayerName() 
                    + " - " + scores.get(i).getSavings());
            }
        }
        System.out.println("==========================================");
    }

    /**
     * Returns the list of high scores.
     * @return list of model.HighScore entries
     */
    public List<HighScore> getScores(){
        return scores;
    }
}