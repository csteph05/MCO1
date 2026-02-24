import java.sql.SQLOutput;
import java.util.*;
// import com.google.gson.Gson;

public class Controller {
    private Player player;
    private Scanner scanner;
    private boolean isRunning;
    private final String BLUE = "\u001B[36m";  // Blue for watered
    private final String GREEN = "\u001B[32m"; // Green for normal plants
    private final String RESET = "\u001B[0m";  // Resets color back to normal

    public Controller(Player player, Scanner scanner){
        this.player = player;
        this.scanner = scanner;
        this.isRunning = true;
    }

    public void startGame(){
        while (isRunning && player.getCurrentDay() <= 15){

            displayGame();
            displayFarm();
            displayMenu();
            handleInput();
        }
        System.out.println("\n==========================================");
        System.out.println("   Season Ended! Final Savings: " + player.getSavings());
        System.out.println("==========================================");
    }

    private void displayMenu(){
        System.out.println("[1] Plant a seed");
        System.out.println("[2] Water a plant");
        System.out.println("[3] Refill watering can");
        System.out.println("[4] Apply fertilizer");
        System.out.println("[5] Remove or harvest a plant");
        System.out.println("[6] Next day");
        System.out.println("[0] Quit");
        System.out.print("Enter your choice: ");
    }

    private void handleInput(){
        String input = scanner.nextLine();
        switch(input){
            case "1":
                    plantSeed();
                    break;
            case "2":
                    waterPlant();
                    break; // waterplant()
            case "3":
                    refillCan();
                    break; // refillcan()
            case "4": break; // applyFertilizer()
            case "5": break; //removeorHarvest()
            case "6": break; //nextDay()
            case "0": isRunning = false; break; // close game so isRunning = false I guess? not sure if necessary
            default:
                System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                break;
        }
    }

    private void displayGame(){
        System.out.println("\n==========================================");
        System.out.println("               VERDANT SUN               ");
        System.out.println("\n==========================================");
        System.out.println(" Day:" + player.getCurrentDay() + "\t\t\t     Savings: " + player.getSavings() + "\n");
        System.out.println("\tWater Can Level: " + player.getWateringCan().getCurrentWtrLevel() + " / " + player.getWateringCan().getMaxWtrLevel() + "\n");
        System.out.println("\n==========================================");
    }

    private void displayFarm() {
        Field farm = player.getFarm();
        System.out.println("                FARM GRID               ");
        System.out.println("==========================================");

        // Print Column  (0 to 9) at the very top
        System.out.print("   ");
        for (int c = 0; c < farm.getCols(); c++) {
            System.out.print(" " + c + "  ");
        }
        System.out.println();


        for (int r = 0; r < farm.getRows(); r++) {
            System.out.print(r + "  ");

            // Loop through every column sa row
            for (int c = 0; c < farm.getCols(); c++) {
                Soil currentTile = farm.getTile(r, c);
                Plant currentPlant = currentTile.getPlant();

                // Check what is currently on the tile (IGNORE THIS FOR NOW)
                // TODO: Double check if this is final
                if (currentTile.isBlocked()) {
                    System.out.print("[X] "); // Meteorite
                } else if (currentPlant == null) {
                    System.out.print("[-] "); // Empty soil
                } else {
                    char plantIcon = currentPlant.getName().charAt(0);// gets first letter of plant for the icon on grid
                    if (currentPlant.isWatered()) {
                        System.out.print("[" + BLUE + plantIcon + RESET + "] "); // may reset para the rest isn't the same color
                    }else {
                        System.out.print("[" + GREEN + plantIcon + RESET + "] "); // may reset para the rest isn't same color
                    }

                }
            }
            System.out.println(); // Jump to the next line after finishing a row
        }
        System.out.println("==========================================");
    }

    /**
     * TODO: another menu that lists all plants that can be planted
     * TODO: if not enough money, dont show, cancel action function, back to main menu
     * TODO: interface that allows player to perform action on multiple tiles
     * TODO: Edge cases, natypo ko 01 tapos 1 kinuha niya imbis na 0,1 lalagay ko so dapat edge cases na isa lang
     */

    private void plantSeed(){
        System.out.print("Enter row (0-9): ");
        int row = Integer.parseInt(scanner.nextLine());
        System.out.print(" Enter col (0-9: ");
        int col = Integer.parseInt(scanner.nextLine());

        Soil tile = player.getFarm().getTile(row, col);

        // check lahat
        if (tile == null || tile.isOccupied() || tile.isBlocked()) {
            System.out.println("Error: cannot plant here.");
            return;
        }
        // TODO: place holder lang to na plant to test need natin gumawa ng menu

        Plant newPlant = new Plant("turnip", 5.0f, 2.0f, 2, 2, "loam");

        // check if afford
        if (!player.canAfford((int) newPlant.getSeedPrice())){
            System.out.println("Error: not enough money! the Seed costs: " + newPlant.getSeedPrice());
        }

        // actual planted logic
        if (tile.plantSeed(newPlant)){
            player.deductSavings((int) newPlant.getSeedPrice());
            System.out.println("You have successfully planted " + newPlant.getName() + " at (" + row + "," + col + ")!");
        }
    }

    /**
     *
     *
     *
     */

    private void waterPlant() {
        int wateredCounter = 0;

        // Check if we have water
        if (player.getWateringCan().getCurrentWtrLevel() <= 0) {
            System.out.println("Your watering can is empty! please refill it.\n");
            return;
        }
        String input = "";
        // allows for multiple inputs to be watered at the same time
        while (true) {
            System.out.println("Enter a single tile (0,0) or multiple (0,0 , 1,1). ");
            System.out.println("Type '0' to cancel and return to Main Menu.");
            System.out.print("Input: ");

            // .trim cleans up the input, removes accidental spaces
            input = scanner.nextLine().trim();

            // cancel check
            if (input.equals("0")) {
                System.out.println("Watering action cancelled. Returning to Main Menu..\n");
                return;
            }

            if (!input.contains(",")) { // catches input na walang ,
                System.out.println("Invalid input! we are looking for numbers formatted with a comma, Please try again.");
                continue; // loops back to enter single tile prompt
            }
            break;
        }
        // .split cuts the input depending on whether it has a "space"
        String[] coords = input.split(" "); // ex: 0,0 0,1 = ["0,0", "1,1"]
        // Loop through every coordinate they typed
        for (String target : coords) {

            // Stop if we run out of water midway through coordinates inputted
            if (player.getWateringCan().getCurrentWtrLevel() <= 0) {
                System.out.println("Watering can is empty! Stopping early.\n");
                break;
            }

            try {
                // Split "0,0" into row 0 and col 0, cuts it at the comma
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0]); // index 0 row
                int col = Integer.parseInt(parts[1]); // index 1 col

                Soil tile = player.getFarm().getTile(row, col);

                // does tile exist, does it have a plant
                if (tile == null || !tile.isOccupied()) {
                    System.out.println("There is no plant to water at (" + target + ")!");
                    continue; // move to next coordinate
                }
                if (tile.getPlant().isWatered()) { // is it already watered
                    System.out.println("Plant at (" + target + ") is already watered.");
                    continue; // move to next tile
                }

                // updates boolean to true
                tile.waterSoil();
                player.getWateringCan().useWater(); // subtracts from watering can's current level
                wateredCounter++;
                System.out.println("Successfully watered the " + tile.getPlant().getName() + " at (" + target + ")!");

            } catch (Exception e) {
                System.out.println(target + " isn't a proper coordinate, skipping.");
            }
        }
        System.out.println("You watered: " + wateredCounter + " plants!\n");
    }
    /**
     * Refills the player's watering can to its maximum capacity.
     */

    private void refillCan(){

        if (player.getWateringCan().getCurrentWtrLevel() == player.getWateringCan().getMaxWtrLevel()){
            System.out.println("Your watering can is already full!\n");
            return;
        }

        if (player.getSavings() >= 100){ // check if we have enough money
            player.deductSavings(100);
            player.getWateringCan().refill();
            System.out.println("Watering can has successfully been refilled.\n");// take money
        } else {
            System.out.println("You don't have enough money! a refill costs 100.\n");
        }
    }
}
