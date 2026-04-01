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
     * asses the "Energizing" stage name to the parent model.GrowthStage class
     *
     */

    public EnergizingStage(){
        super("Energizing");
    }

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered){
        return 0;
    }

    @Override
    public int getYieldMultiplier(){
        return 0;
    }

    @Override
    public int getFertilizerDaysConsumed(){
        return 1;
    }
}
