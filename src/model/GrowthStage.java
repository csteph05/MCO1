package model;

/**
 * Abstract superclass representing a generic growth stage of a plant.
 *
 */

public abstract class GrowthStage {

    protected String stageName;

    /**
     * Constructor for the model.GrowthStage.
     *
     * @param stageName the name of the current stage
     */

    public GrowthStage(String stageName){
        this.stageName = stageName;
    }

    /**
     * Returns the name of the stage
     *
     * @return stageName
     */

    public String getStageName(){
        return stageName;
    }

    /**
     * Calculates how many stages the plant should progress based on current state.
     *
     * @param hasPreferredSoil true if planted on the plant's preferred soil
     * @param hasFertilizer true if the soil currently has active fertilizer
     * @param isWatered true if the plant was watered today
     * @return the number of stages to advance (e.g., 0, 1, or up to 5)
     */

    public abstract int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered);

    /**
     * Determines the crop yield multiplier if harvested at this stage.
     * @return 0 for no crop, 1 for normal yield, 2 for high productive yield.
     */

    public abstract int getYieldMultiplier();

    /**
     * Determines the fertilizer consumption rate.
     * @return 1 for normal stages or 2 for specific stages.
     */

    public abstract int getFertilizerDaysConsumed();
}