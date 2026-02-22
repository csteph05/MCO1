public class Soil {
    private String name;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean isBlocked;

    /**
     * Constructs a Soil tile with a given soil type.
     * Initially, no plant or fertilizer is present and it is unblocked.
     *
     * @param name the type of the soil
     */

    public Soil(String name) {
        this.name = name;
        this.plant = null;
        this.fertilizer = null;
        this.isBlocked = false;
    }

    /**
     * Returns the type of soil
     *
     * @return soil type as a string
     */

    public String getName(){
        return name;
    }

    /**
     * Returns the plant currently on the soil
     *
     * @return the plant object, or null if none
     */
    public Plant getPlant(){
        return plant;
    }

    /**
     * Returns the fertilizer applied on the soil
     *
     * @return the Fertilizer object, or null if none
     */
    public Fertilizer getFertilizer(){
        return fertilizer;
    }

    /**
     * Returns whether soil tile is blocked
     *
     * @return true if blocked, false otherwise
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Sets a plant on the soil tile
     *
     * @param plant the Plant object to place on the soil
     */
    public void setPlant(Plant plant){
        this.plant = plant;
    }

    /**
     * Sets a fertilizer on the soil tile
     *
     * @param fertilizer the fertilizer to apply
     */

    public void setFertilizer(Fertilizer fertilizer){
        this.fertilizer = fertilizer;
    }

    /**
     *   Sets whether this soil tile is blocked or not
     *
     * @param blocked true if blocked, false to unblock
     */
    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }


}
