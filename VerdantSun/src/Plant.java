public class Plant {

    // Basic info about the plant
    private final String name;
    private float sellPrice;
    private float seedPrice;
    private int yield;

    // growth stat
    private int maxGrowth;
    private int currentGrowth;

    // Current state
    private String preferredSoil;
    private boolean isWatered;


    /**
     *  Constructor that creates a Plant instance with its name, sell/seed price, yield
     *  maxGrowth, and it's preferredSoil.
     *
     * @param name is the name of the Plant
     * @param sellPrice is the selling price per yield
     * @param seedPrice is the cost of planting the seed
     * @param yield is the number of crops produced
     * @param maxGrowth
     * @param preferredSoil is the preferred soil type of the plant
     */
    public Plant(String name, float sellPrice, float seedPrice,
                 int yield, int maxGrowth, String preferredSoil) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.seedPrice = seedPrice;
        this.yield = yield;
        this.maxGrowth = maxGrowth;
        this.currentGrowth = 0; // specs: current Growth starts at 0
        this.preferredSoil = preferredSoil;
        this.isWatered = false; // specs: Defaults to false
    }

    /**
     * Returns the name of the Plant.
     *
     * @return the player's name
     */

    public String getName() {
        return name;
    }

    /**
     *  Returns the sellPrice of the Plant.
     *
     * @return the sellPrice
     */

    public float getSellPrice() {
        return sellPrice;
    }

    /**
     * Returns the seedPrice of the Plant.
     *
     * @return the seedPrice
     */

    public float getSeedPrice() {
        return seedPrice;
    }

    /**
     * Returns the yield of the Plant.
     *
     * @return the yield
     */

    public int getYield(){
        return yield;
    }

    /**
     * Returns the maximum growth stage of the Plant.
     *
     * @return the maxGrowth
     */

    public int getMaxGrowth(){
        return maxGrowth;
    }

    /**
     * Returns the current growth stage of the Plant.
     *
     * @return the currentGrowth
     */
    public int getCurrentGrowth(){
        return currentGrowth;
    }

    /**
     * Returns the preferredSoil type of the plant
     *
     * @return the preferredSoil type
     */
    public String getPreferredSoil(){
        return preferredSoil;
    }

    /**
     * Returns whether the Plant has been watered.
     *
     * @return true if watered, false is not watered
     */
    public boolean isWatered(){
        return isWatered;
    }

    /**
     * Sets the watered status of the Plant.
     *
     * @param watered True if plant is watered, false otherwise
     */
    public void setWatered(boolean watered){
        this.isWatered = watered;
    }

}




