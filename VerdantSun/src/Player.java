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

        this.farm = new Field(); // every new game by the player starts with the field
        this.currentDay = 1; // every new game starts at day 1
    }

    /**
     * Adds the specified amount to the player's savings.
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
     * * Deducts the specified amount from the player's savings if affordable.
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

    // public void nextDay from sherimie for some reason

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

}
