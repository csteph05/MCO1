public class Plant {

    // Basic info about the plant
    private final String name;
    private float sellPrice;
    private float seedPrice;
    private int yield;

    // growth stat
    private int maxGrowth;
    private int currentGrowth;

    // Current state
    private String preferredSoil;
    private boolean isWatered;

    // plant constructor
    public Plant(String name, float sellPrice, float seedPrice,
                 int yield, int maxGrowth, String preferredSoil){
        this.name = name;
        this.sellPrice = sellPrice;
        this.seedPrice = seedPrice;
        this.yield = yield;
        this.maxGrowth = maxGrowth;
        this.currentGrowth = 0; // specs: current Growth starts at 0
        this.preferredSoil = preferredSoil;
        this.isWatered = false; // specs: Defaults to false
    }

    // todo: getters
    // todo: methods, something for isWatered flag, grow etc.
}




