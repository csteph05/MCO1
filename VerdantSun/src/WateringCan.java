public class WateringCan {
    private final int maxWtrLevel = 10;
    private int currentWtrLevel;

    /**
     *
     * Constructor that initialize the watering can
     * to full capacity when a new object is created
     *
     */

    public WateringCan(){
        this.currentWtrLevel = maxWtrLevel;
    }

    /**
     * Returns the maximum water cap of the watering can
     *
     * @return maximum water level
     */

    public int getMaxWtrLevel(){
        return maxWtrLevel;
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
        return currentWtrLevel == maxWtrLevel;
    }

    /**
     * Refills the watering can to maximum cap
     *
     */

    public void refill(){
        this.currentWtrLevel = maxWtrLevel;
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
