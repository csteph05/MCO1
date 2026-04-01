package model;

/**
 * this class represents a single grid tile on the farm
 * it manages the soil type, what plant is currently growing on it,
 * applied fertilizers, and states like being blocked when there is a meteorite
 *
 */
public class Soil {
    private String name;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean isBlocked;
    private boolean isFertilizerPermanent;

    /**
     * Constructs a model.Soil tile with a given soil type.
     * Initially, no plant or fertilizer is present and it is unblocked.
     *
     * @param name the type of the soil
     */

    public Soil(String name) {
        this.name = name;
        this.plant = null;
        this.fertilizer = null;
        this.isBlocked = false;
        this.isFertilizerPermanent = false;
    }

    /**
     * checks if a plant is currently growing on this soil.
     *
     * @return true if occupied
     */

    public boolean isOccupied(){
        return plant != null;
    }

    /**
     * Checks if fertilizer is currently applied.
     *
     * @return true if fertilized
     */

    public boolean hasFertilizer() {
        return fertilizer != null;
    }

    /**
     * Attempts to plant a seed if the tile is available.
     *
     * @param plant the model.Plant object to place
     * @return true if successful, false if blocked or occupied
     */
    public boolean plantSeed(Plant plant) {
        if (!isOccupied() && !isBlocked) {
            this.plant = plant;
            return true;
        }
        return false;
    }

    /**
     * This is the method that removes a plant from the soil or harvests it if it is mature.
     * returns 0.0 if the plant is somehow destroyed / the crop value if harvest
     *
     * @return harvest value, or 0.0f if not mature or empty
     */

    public float removeOrHarvest() {
        if (plant == null) {
            return 0.0f;
        }
        float value = 0.0f;
        if (plant.isMature()) {
            value = plant.harvestValue();
        }
        plant = null;
        return value;
    }

    /**
     * this is the method that applies fertilizer to the soil.
     *
     * @param fertilizer the fertilizer to apply
     * @return true if successful
     */

    public boolean applyFertilizer(Fertilizer fertilizer) {
        if (!hasFertilizer()) {
            this.fertilizer = fertilizer;
            return true;
        }
        return false;
    }

    /**
     * this is the method that removes fertilizer from the soil by setting it to null.
     * checks for supposed permanently fertilized tiles to avoid being removed.
     */

    public void removeFertilizer() {
        if (!isFertilizerPermanent) {
            this.fertilizer = null;
        }
    }

    /**
     * this is the method that waters the plant on this soil if it exists.
     *
     * @return true if watering was successful
     */

    public boolean waterSoil() {
        if (isOccupied()) {
            plant.watered(); // Calls the water method in model.Plant
            return true;
        }
        return false;
    }

    /**
     * this is the method that advances the soil tile to the next day.
     * Handles plant growth (if the plant is watered), water status, and daily fertilizer decay.
     */

    public void nextDay(){
        if(isOccupied() && plant.isWatered()){
            int stages = plant.getGrowthStagesPerDay(this.name, hasFertilizer());
            plant.grow(stages);
            plant.resetWater();

            if(hasFertilizer() && !isFertilizerPermanent()){
                fertilizer.reduceEffectDays();
                if(fertilizer.isExpired()){
                    removeFertilizer();
                }
            }

        }
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
     * @return the model.Fertilizer object, or null if none
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
     * @param plant the model.Plant object to place on the soil
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

    /**
     *  Checks if the fertilizer applied is permanent
     *
     * @return true if fertilizer is permanent, false otherwise
    */

    public boolean isFertilizerPermanent() {
        return isFertilizerPermanent;
    }

    /**
     * Updates the status of the applied fertilizer
     *
     * @param permanent the boolean flag to set to the fertilizer
     */

    public void setFertilizerPermanent(boolean permanent) {
        this.isFertilizerPermanent = permanent;
    }

}
