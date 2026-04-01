package model;

/**
 * Represents the Fully Mature stage of a plant's life cycle.
 * This is the final stage of growth. The plant cannot grow any further,
 * regardless of water, soil, or fertilizer. It is ready to be harvested.
 */

public class FullyMatureStage extends GrowthStage {

    /**
     * Constructor for the FullyMatureStage.
     * Passes the "Fully Mature" stage name to the parent model.GrowthStage class
     */
    public FullyMatureStage() {
        super("Fully Mature");
    }

    /**
     * Calculates the overnight growth.
     *
     * @param hasPreferredSoil ignored
     * @param hasFertilizer ignored
     * @param isWatered ignored
     *
     * @return 0, because the plant has reached its maximum stage and cannot grow anymore.
     */

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered) {
        return 0;
    }

    /**
     * Determines what the crop yield multiplier is if the players harvests the plant
     *
     * @return 1, harvesting a plant at the "Fully Mature" stage produces exactly the base yield.
     */

    @Override
    public int getYieldMultiplier() {
        return 1;
    }

    /**
     * Determines the fertilizer consumption rate.
     *
     * @return 1, representing the normal fertilizer consumption rate.
     */

    @Override
    public int getFertilizerDaysConsumed() {
        return 1;
    }
}
