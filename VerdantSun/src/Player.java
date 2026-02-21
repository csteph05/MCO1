public class Player {
    private String playerName;
    private int savings;
    private WateringCan wateringCan;
    private Field farm;
    private int currentDay;

    /**
     *  Constructor that creates a Player instance with a name, starting savings, and a watering can
     *
     * @param playerName is the name of the current Player
     * @param savings pertains to the current savings of the player
     * @param wateringCan is the wateringCan obj of the player
     */
    public Player(String playerName, int savings, WateringCan wateringCan){
        this.playerName = playerName;
        this.savings = savings;
        this.wateringCan = wateringCan;
    }

    /**
     * Returns the name of player.
     *
     * @return the player's name
     */

    public String getName(){
        return playerName;
    }
    /**
     * Returns the savings of player.
     *
     * @return the player's current savings
     */

    public int getSavings(){
        return savings;
    }

    /**
     * Returns the player's wateringCan
     *
     * @return the wateringCan object owned by the player
     */

    public WateringCan getWateringCan(){
        return wateringCan;
    }

    /**
     * Returns the player's farm
     *
     * @return the field object associated with the player
     */

    public Field getFarm(){
        return farm;
    }

    /**
     * Returns the current day of the player
     *
     * @return current day as an integer
     */

    public int getCurrentDay(){
        return currentDay;
    }
    /**
     * Sets the savings of the player
     *
     * @param savings is an integer that is the savings of the player
     */

    public void setSavings(int savings){
        this.savings = savings;
    }

}
