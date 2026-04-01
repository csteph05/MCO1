/**
 * This class represents the watering can tool used by the player to water crops
 * it manages the currentwtrlevel, max capacity, and the logic for using
 * water or refilling the can during the game
 *
 */
public class WateringCan {
    private final int MAX_WTR_LEVEL = 10;
    private int currentWtrLevel;

    /**
     *
     * Constructor that initialize the watering can
     * to full capacity when a new object is created
     *
     */

    public WateringCan(){
        this.currentWtrLevel = MAX_WTR_LEVEL;
    }

    /**
     * Returns the maximum water cap of the watering can
     *
     * @return maximum water level
     */

    public int getMaxWtrLevel(){
        return MAX_WTR_LEVEL;
    }

    /**
     *
     * Returns the current water level of the watering can
     *
     * @return current water level
     */

    public int getCurrentWtrLevel(){
        return currentWtrLevel;
    }

    /**
     * Checks if the watering can is completely empty
     *
     *
     * @return true if empty, false otherwise
     */

    public boolean isEmpty() {

        return currentWtrLevel == 0;
    }

    /**
     * Checking if the watering can is at maximum capacity.
     *
     * @return true if full, false otherwise
     */

    public boolean isFull(){
        return currentWtrLevel == MAX_WTR_LEVEL;
    }

    /**
     * Refills the watering can to maximum cap
     *
     */

    public void refill(){
        this.currentWtrLevel = MAX_WTR_LEVEL;
    }

    /**
     * Deducts 1 unit of water if the can is not empty
     *
     * @return true if water was used successfully, false if the can was empty
     */

    public boolean useWater(){
        if (currentWtrLevel > 0){
            currentWtrLevel--;
            return true;
        }
        return false;
    }
}
