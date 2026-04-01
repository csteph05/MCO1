package model;

/**
 * Represents the "High Productive" stage of a plant's growth cycle
 * In this stage, the plant grows normally when watered and fertilized.
 * However, Harvesting a plant yields twice the standard amount of crops.
 */

public class HighProductiveStage extends GrowthStage{

    /**
     * Constructor for the HighProductiveStage
     * Passes the "High Productive" stage name to the parent model.GrowthStage class
     */

    public HighProductiveStage(){
        super("High Productive");
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
     * @return 2, harvesting a plant at the "High Productive" stage produces twice the base yield
     */

    @Override
    public int getYieldMultiplier(){
        return 2;
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
