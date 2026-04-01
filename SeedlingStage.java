/**
 * Represents the "Seedling" stage of a plant's growth cycle
 * requires water to grow, yield no crops if harvested prematurely,
 * and receives double the growth bonuses from preferred soil and fertilizer.
 *
 */
public class SeedlingStage extends GrowthStage {

    /**
     * Constructor for the "Seedling" stage
     * Passes the "Seedling" stage name to the parent GrowthStage class
     *
     */

    public SeedlingStage(){
        super("Seedling");
    }

    /**
     * Calculates the number of stages the seedling will grow overnight
     * Seedlings get a growth multiplier as a normal +1 receives a +2
     *
     * @param hasPreferredSoil true if planted on the plant's preferred soil
     * @param hasFertilizer true if the soil currently has fertilizer
     * @param isWatered true if the plant was watered today
     *
     * @return the total number of stages to advance, maximum of 5 stages
     */

    @Override
    public int calculateGrowth(boolean hasPreferredSoil, boolean hasFertilizer, boolean isWatered){

        if (!isWatered){
            return 0;
        }
        int totalGrowth = 1; // base growth for being watered is 1 stage

        // normal bonus is + 1 but at current stage it is doubled
        if (hasPreferredSoil){
            totalGrowth += 2;
        }

        if (hasFertilizer){
            totalGrowth += 2;
        }

        // max amt of growth would be 5 stages
        return totalGrowth;
    }

    /**
     * Determines what the crop yield multiplier is if the players harvests the plant
     *
     * @return 0, harvesting a plant at the "Seedling" stage yields nothing.
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
