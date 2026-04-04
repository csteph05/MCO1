package model;

/**
 * Represents the "Energizing" stage of a plant's growth cycle
 * In this stage, the plant is actively storing nutrients and acts as a sponge,
 * consuming fertilizer twice as fast. Watering the plant disrupts this process
 * and halts its growth entirely
 */

public class EnergizingStage extends GrowthStage {

    /**
     * Constructor for the model.EnergizingStage
     * Passes the "Energizing" stage name to the parent model.GrowthStage class
     *
     */

    public EnergizingStage(){
        super("Energizing");
    }

    /**
     * Calculates the overnight growth of the plant.
     * Watering the plant during this stage halts its progression.
     *
     * @param hasPreferredSoil true if planted on the plant's preferred soil
     * @param hasFertilizer true if the soil currently has active fertilizer
     * @param isWatered true if the plant was watered today
     *
     * @return 0 if watered, 1 if left alone to grow.
     */

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered){

        if (isWatered){
            return 0;
        }

        return 1;
    }

    /**
     * Determines what the crop yield multiplier is if the players harvests the plant
     *
     * @return 0, harvesting a plant at the "Energizing" stage yields nothing.
     */

    @Override
    public int getYieldMultiplier(){
        return 0;
    }

    /**
     * Determines the fertilizer consumption rate.
     *
     * @return 1, representing the normal fertilizer consumption rate.
     */

    @Override
    public int getFertilizerDaysConsumed(){
        return 2;
    }
}
