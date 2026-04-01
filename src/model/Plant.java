package model;

import java.util.List;

/**
 * This class represents a crop that can be planted on the farm grid
 * it holds the base stats of the seed: name, sell/seed price,
 * mature days, yield, preferred soil type.
 *
 */
public class Plant {

    private final String name;
    private float sellPrice;
    private float seedPrice;
    private int yield;
    private int maxGrowth;
    private int currentGrowth;
    private String preferredSoil;
    private boolean isWatered;

    /**
     *  Constructor that creates a model.Plant instance with its name, sell/seed price, yield
     *  maxGrowth, and it's preferredSoil.
     *
     * @param name is the name of the model.Plant
     * @param sellPrice is the selling price per yield
     * @param seedPrice is the cost of planting the seed
     * @param yield is the number of crops produced
     * @param maxGrowth is to track if the plant is already mature / ready to be harvested
     * @param preferredSoil is the preferred soil type of the plant
     */

    public Plant(String name, float sellPrice, float seedPrice,
                 int yield, int maxGrowth, String preferredSoil) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.seedPrice = seedPrice;
        this.yield = yield;
        this.maxGrowth = maxGrowth;
        this.currentGrowth = 0;
        this.preferredSoil = preferredSoil;
        this.isWatered = false;
    }

    /**
     * Calculates the total growth stages the plant gains in a day.
     * The plant grows by a base of 1, with bonuses for preferred soil and fertilizer.
     *
     * @param soilType the type of soil plant is currently in
     * @param hasFertilizer true if fertilizer is applied to the soil tile
     *
     * @return the total num of growth stages for the day
     */
    public int getGrowthStagesPerDay(String soilType, boolean hasFertilizer){
        int stages = 1; // base growth
        if(soilType.equals(this.preferredSoil)) stages += 1; // model.Soil bonus
        if(hasFertilizer) stages += 1; // fertilizer bonus
        return stages;
    }

    /**
     * Increases current growth stage of the plant by specific amt
     * growth is capped at plant's maximum growth stage
     *
     * @param stages the number of growth stages to add to the plant
     */

    public void grow(int stages) {
        currentGrowth += stages;
        if (currentGrowth > maxGrowth) {
            currentGrowth = maxGrowth;
        }
    }

    /**
     * Checks if the plant has reached its maximum growth.
     *
     * @return true if mature
     */

    public boolean isMature() {
        return currentGrowth >= maxGrowth;
    }

    /**
     * Sets the watered status to true.
     * plant has received water for the current day
     */
    public void watered() {
        this.isWatered = true;
    }

    /**
     * Resets the watered status of the plant to false.
     * called at start of a new day
     */
    public void resetWater() {
        this.isWatered = false;
    }

    /**
     * Calculates the total value of the plant based on yield.
     * Named specifically for model.Soil's removeOrHarvest() call.
     *
     * @return total harvest value, or 0.0f if not mature
     */

    public float harvestValue() {
        if (isMature()) {
            return sellPrice * yield;
        }
        return 0.0f;
    }

    /**
     * Gets the cheapest seed price from the list of plants
     *
     * @param plants the list of available plants loaded from JSON
     * @return the price of the cheapest seed
     */
    public static int getCheapestSeed(List<Plant> plants){
        if (plants == null || plants.isEmpty()) return 0;

        float min = plants.get(0).getSeedPrice();
        for (Plant p : plants){
            if (p.getSeedPrice() < min){
                min = p.getSeedPrice();
            }
        }
        return (int) min;
    }

    /**
     * Returns the name of the model.Plant.
     *
     * @return the player's name
     */

    public String getName() { return name; }

    /**
     *  Returns the sellPrice of the model.Plant.
     *
     * @return the sellPrice
     */

    public float getSellPrice() { return sellPrice; }

    /**
     * Returns the seedPrice of the model.Plant.
     *
     * @return the seedPrice
     */

    public float getSeedPrice() { return seedPrice; }

    /**
     * Returns the yield of the model.Plant.
     *
     * @return the yield
     */

    public int getYield() { return yield; }

    /**
     * Returns the maximum growth stage of the model.Plant.
     *
     * @return the maxGrowth
     */

    public int getMaxGrowth() { return maxGrowth; }

    /**
     * Returns the current growth stage of the model.Plant.
     *
     * @return the currentGrowth
     */

    public int getCurrentGrowth() { return currentGrowth; }

    /**
     * Returns the preferredSoil type of the plant
     *
     * @return the preferredSoil type
     */

    public String getPreferredSoil() { return preferredSoil; }

    /**
     * Returns whether the model.Plant has been watered.
     *
     * @return true if watered, false is not watered
     */

    public boolean isWatered() {
        return isWatered;
    }

    /**
     * Sets the watered status of the model.Plant.
     *
     * @param watered True if plant is watered, false otherwise
     */

    public void setWatered(boolean watered) {
        this.isWatered = watered;
    }
}