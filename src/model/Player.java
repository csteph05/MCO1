package model;

/**
 * this class represents the user playing the farming simulator
 * it manages the player's profile, their current financial savings,
 * their watering can tool. the farm grid they are working on, and
 * tracks the current in-game day
 */
public class Player {
    private String playerName;
    private int savings;
    private WateringCan wateringCan;
    private Field farm;
    private int currentDay;
    private int excavatedToday;

    /**
     *  Constructor that creates a model.Player instance with a name, starting savings, and a watering can
     *
     * @param playerName is the name of the current model.Player
     * @param savings pertains to the current savings of the player
     * @param wateringCan is the wateringCan obj of the player
     */
    public Player(String playerName, int savings, WateringCan wateringCan){
        this.playerName = playerName;
        this.savings = savings;
        this.wateringCan = wateringCan;

        this.farm = new Field(); // every new game by the player starts with the field
        this.currentDay = 1; // every new game starts at day 1
        this.excavatedToday = 0; // resets every new day
    }

    /**
     * adds the specified amount to the player's savings.
     * Used when harvesting crops or gaining daily income.
     *
     * @param amount the amount to add to savings
     */
    public void addSavings(int amount){
        if(amount > 0){
            this.savings += amount;
        }
    }

    /**
     * Increments the daily excavation counter by 1
     * Called when the player excavates a blocked tile
     *
     */
    public void addExcavationCounter(){
        this.excavatedToday++;
    }

    /**
     * Deducts the specified amount from the player's savings if affordable.
     * Used when buying seeds, fertilizer, or refilling the watering can.
     *
     * @param amount the amount to deduct
     * @return true if deduction was successful, false if insufficient funds
     */
    public boolean deductSavings(int amount){
        if(amount > 0 && savings >= amount){
            savings -= amount;
            return true;
        }
        return false;
    }

    /**
     * Checks if the player has enough savings to afford a purchase.
     *
     * @param amount the cost to check against
     * @return true if player can afford it, false otherwise
     */
    public boolean canAfford(int amount){
        return savings >= amount;
    }

    /**
     * This is the method that advances the player's state to the next day.
     * It increments the current day, gives the daily 50 savings, and triggers the field's next day logic
     * 
     */
    public void nextDay(){
        farm.nextDay();
        savings += 50;
        currentDay++;
        excavatedToday = 0; // resets the counter for today
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
     * Returns the number of times the player has excavated a meteorite.
     *
     * @return the number of excavations today as an integer
     */
    public int getExcavatedToday(){
        return excavatedToday;
    }
}
