public class Fertilizer {
    private String name;
    private float price;
    private int effectDays;


    /**
     * Constructor to create a Fertilizer instance with a name, price, and effect duration
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
     * Decreases the remaining effect duration by 1 day.
     * Replaces the generic setter to prevent illegal values.
     *
     */

    public void reduceEffectDays() {
        if (effectDays > 0) {
            effectDays--;
        }
    }

    /**
     * Returns the name of the Fertilizer
     *
     * @return the name
     */

    public String getName(){
        return name;
    }

    /**
     * Returns the price of the Fertilizer
     *
     * @return the price
     */

    public float getPrice() {
        return price;
    }

    /**
     * Returns the number of remaining effect days for the Fertilizer
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
