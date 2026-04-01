package model;

/**
 * Represents the "Low Productive" stage of a plant's growth cycle
 * In this stage, the plant grows normally when watered and fertilized.
 * Harvesting a plant yields the standard amount of crops.
 */

public class LowProductiveStage extends GrowthStage{


    /**
     * Constructor for the LowProductiveStage
     * Passes the "Low Productive" stage name to the parent model.GrowthStage class
     */

    public LowProductiveStage(){
        super("Low Productive");
    }

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered){

        if (!isWatered){
            return 0;
        }

        int totalGrowth = 1; // base growth for being watered is 1

        // normal bonus is + 1
        if (hasPreferredSoil){
            totalGrowth += 1;
        }

        if (hasFertilizer){
            totalGrowth += 1;
        }

        return totalGrowth;
    }

    /**
     * Determines what the crop yield multiplier is if the players harvests the plant
     *
     * @return 1, harvesting a plant at the "Low Productive" stage produces exactly the base yield.
     */

    @Override
    public int getYieldMultiplier(){
        return 1;
    }

    /**
     * Determines the fertilizer consumption rate.
     *
     * @return 1, representing the normal fertilizer consumption rate.
     */

    @Override
    public int getFertilizerDaysConsumed(){
        return 1;
    }
}
