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
     * Sets the current water level.
     * The controller should manage this when watering plants or refilling
     *
     * @param currentWtrLevel new water level
     */
    public void setCurrentWtrLevel(int currentWtrLevel) {
        this.currentWtrLevel = currentWtrLevel;
    }
}
