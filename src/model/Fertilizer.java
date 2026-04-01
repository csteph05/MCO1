package model;

import java.util.List;

/**
 * this class represents a soil enhancement item in the VerdantSun Simulator.
 * it stores the fertilizer 's name, purchase price, total num of effect days
 * its effect remain active on a tile
 *
 */

public class Fertilizer {
    private String name;
    private float price;
    private int effectDays;


    /**
     * Constructor to create a model.Fertilizer instance with a name, price, and effect duration
     *
     * @param name the name of the fertilizer
     * @param price the price of the fertilizer
     * @param effectDays the remaining effect days the fertilizer will affect the plant
     */

    public Fertilizer(String name, float price, int effectDays) {
        this.name = name;
        this.price = price;
        this.effectDays = effectDays;
    }

    /**
     * Checks if the fertilizer has run out of active days.
     *
     * @return true if effectDays is 0 or less
     */

    public boolean isExpired() {
        return effectDays <= 0;
    }

    /**
     * This is the method that decreases the remaining effect duration of the fertilizer by 1 day.
     * It includes a safety check to ensure the effect days never drop below zero.
     */

    public void reduceEffectDays() {
        if (effectDays > 0) {
            effectDays--;
        }
    }

    /**
     * gets the cheapest fertilizer price from the list
     *
     * @param fertilizers the list of available fertilizers from the JSON file
     * @return int price of cheapest fertilizer
     */
    public static int getCheapestFertilizer (List<Fertilizer> fertilizers){
        if (fertilizers == null || fertilizers.isEmpty()) return 0;

        float min = fertilizers.get(0).getPrice();

        for (Fertilizer f: fertilizers){
            if (f.getPrice() < min){
                min = f.getPrice();
            }
        }

        return (int) min;
    }

    /**
     * Returns the name of the model.Fertilizer
     *
     * @return the name
     */

    public String getName(){
        return name;
    }

    /**
     * Returns the price of the model.Fertilizer
     *
     * @return the price
     */

    public float getPrice() {
        return price;
    }

    /**
     * Returns the number of remaining effect days for the model.Fertilizer
     *
     * @return the effectDays as an integer
     */

    public int getEffectDays() {
        return effectDays;
    }

    /**
     * Setter for the remaining days for the fertilizer
     *
     *
     * @param effectDays the new num of remaining effect days
     */

    public void setEffectDays(int effectDays){
        this.effectDays = effectDays;
    }
}
