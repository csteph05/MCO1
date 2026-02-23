/**
 * Plant represents the crop growing on a soil tile.
 * It handles its own growth stages and harvest value calculations.
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
     * Constructor to create a Plant instance.
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

    // --- BEHAVIOR METHODS ---

    /**
     * Increases current growth by specified stages, capping at maxGrowth.
     */
    public void grow(int stages) {
        currentGrowth += stages;
        if (currentGrowth > maxGrowth) {
            currentGrowth = maxGrowth;
        }
    }

    /**
     * Checks if the plant has reached its maximum growth.
     * @return true if mature
     */
    public boolean isMature() {
        return currentGrowth >= maxGrowth;
    }

    /**
     * Sets the watered status to true.
     */
    public void water() {
        this.isWatered = true;
    }

    /**
     * Resets the watered status to false (typically at the start of a new day).
     */
    public void resetWater() {
        this.isWatered = false;
    }

    /**
     * Calculates the total value of the plant based on yield.
     * Named specifically for Soil's removeOrHarvest() call.
     * @return total harvest value, or 0.0f if not mature
     */
    public float harvestValue() {
        if (isMature()) {
            return sellPrice * yield;
        }
        return 0.0f;
    }

    // --- GETTERS & SETTERS ---

    public String getName() { return name; }
    public float getSellPrice() { return sellPrice; }
    public float getSeedPrice() { return seedPrice; }
    public int getYield() { return yield; }
    public int getMaxGrowth() { return maxGrowth; }
    public int getCurrentGrowth() { return currentGrowth; }
    public String getPreferredSoil() { return preferredSoil; }

    public boolean isWatered() {
        return isWatered;
    }

    // Kept for compatibility, but prefer using water() and resetWater()
    public void setWatered(boolean watered) {
        this.isWatered = watered;
    }
}