package model;

/**
 * Represents the "Dormant" stage of a plant's growth cycle
 * a dormant plant ignores all external factors such as
 * (water, soil, fertilizer ) and simply progresses to the next stage the ff day.
 * yields no crops when harvested
 */
public class DormantStage extends GrowthStage {

    /**
     * Constructor for the model.DormantStage
     * Passes the "Dormant" stage name to the parent model.GrowthStage class
     */
    public DormantStage(){
        super("Dormant");
    }

    /**
     *  Calculates the number of stages the seedling will grow overnight
     *
     * @param hasPreferredSoil true if planted on the plant's preferred soil
     * @param hasFertilizer true if the soil currently has active fertilizer
     * @param isWatered true if the plant was watered today
     *
     * @return 1, because it always progresses one stage, ignoring all bonuses.
     */

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered){
        // the dormant stage specifically states fertilizer and preferred soil, and water
        // have no effect and progresses regardless of water.
        return 1;
    }

    /**
     * Determines what the crop yield multiplier is if the players harvests the plant
     *
     * @return 0, harvesting a plant at the "Dormant" stage yields nothing.
     */

    @Override
    public int getYieldMultiplier(){
        return 0;
    }

    /**
     * Determines the fertilizer consumption.
     *
     * @return 1, representing the normal fertilizer consumption rate.
     */

    @Override
    public int getFertilizerDaysConsumed(){
        return 1;
    }
}
