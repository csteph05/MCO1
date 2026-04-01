package controller;

import java.util.*;
import model.*;
/**
 * this class acts as the main engine for the verdantsun farming simulator
 * manages the core gameplay loop, player inputs, menu selections, interaction
 * between the player and the farm grid and the loaded data from the JSON.
 *
 */
public class Controller {
    private Player player;
    private Scanner scanner;
    private boolean isRunning;
    private List<Plant> plants;
    private List<Fertilizer> fertilizers;
    private HighScoreManager hsManager;

    private final String BLUE   = "\u001B[36m";
    private final String GREEN  = "\u001B[32m";
    private final String RESET  = "\u001B[0m";
    private final String YELLOW = "\u001B[33m";
    private final String RED    = "\u001B[31m";

    /**
     * Constructor for controller.Controller.
     *
     * @param player the current player
     * @param scanner the scanner for input
     * @param plants list of plants loaded from JSON
     * @param fertilizers list of fertilizers loaded from JSON
     * @param hsManager the high score manager
     */
    public Controller(Player player, Scanner scanner,
                      List<Plant> plants, List<Fertilizer> fertilizers,
                      HighScoreManager hsManager){
        this.player = player;
        this.scanner = scanner;
        this.isRunning = true;
        this.plants = plants;
        this.fertilizers = fertilizers;
        this.hsManager = hsManager;
    }

    /**
     * Starts the main game loop. Runs until day 15 or player quits.
     */
    public void startGame(){
        // keep looping until the player types 0 to quit OR they finish day 15
        while(isRunning && player.getCurrentDay() <= 15){
            displayGame();
            displayFarm();
            displayMenu();
            handleInput();
        }
        // check if they actually survived past day 15 to get a highscore
        if (player.getCurrentDay() > 15) {

            System.out.println("\n==========================================");
            System.out.println("   Season Ended! Final Savings: " + player.getSavings());
            System.out.println("==========================================");

            // check if they're qualified to be added to the top 10
            boolean qualified = hsManager.addScore(player.getName(), player.getSavings());
            if (qualified) {
                System.out.println("You made it to the High Score table!");
            } else {
                System.out.println("You did not qualify for the High Score table.");
            }
            // prints the final leaderboard
            hsManager.displayScores();
            // save the updated leaderboard to the JSON file so it stays for the next game
            JsonLoader.saveHighScores("HighScores.json", hsManager);
        } else {
            System.out.println("\n Game quit early. play again next time!!");
        }
    }

    /**
     *  Method that prints the main menu
     */
    private void displayMenu(){
        System.out.println("\n==========================================");

        if (player.canAfford(Plant.getCheapestSeed(plants))){
            System.out.println("[1] model.Plant a seed");
        }
        if(!player.getWateringCan().isEmpty()){
            System.out.println("[2] Water a plant");
        }
        if(!player.getWateringCan().isFull() && player.canAfford(100)){
            System.out.println("[3] Refill watering can");
        }
        if (player.canAfford(Fertilizer.getCheapestFertilizer(fertilizers))){
            System.out.println("[4] Apply fertilizer");
        }
        System.out.println("[5] Remove or harvest a plant");
        System.out.println("[6] Next day");

        if (player.getCurrentDay() >= 8 && player.canAfford(500)){
            System.out.println("[7] Excavate meteorite");
        }
        System.out.println("[0] Quit");
        System.out.print("Enter your choice: ");
    }

    /**
     * method that reads the number typed by the player
     * which triggers the corresponding action
     */
    private void handleInput(){
        String input = scanner.nextLine().trim();

        switch(input){
            case "1":
                if (player.canAfford(Plant.getCheapestSeed(plants))){
                    plantSeed();
                }
                break;
            case "2":
                if (!player.getWateringCan().isEmpty()){
                 waterPlant();
                }
                break;
            case "3":
                if (!player.getWateringCan().isFull() && player.canAfford(100)){
                    refillCan();
                }
                break;
            case "4":
                if (player.canAfford(Fertilizer.getCheapestFertilizer(fertilizers))){
                    applyFertilizer();
                }
                break;
            case "5":
                removeOrHarvest();
                break;
            case "6":
                nextDay();
                break;
            case "7":
                if (player.getCurrentDay() >= 8 && player.canAfford(500)){
                excavateMeteorite();
                }
                break;
            case "0":
                isRunning = false;
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Displays current day, savings, and water level.
     */
    private void displayGame(){
        System.out.println("\n==========================================");
        System.out.println("               VERDANT SUN               ");
        System.out.println("==========================================");
        System.out.println(" Day: " + player.getCurrentDay()
            + "\t\t\tSavings: " + player.getSavings());
        System.out.println(" Water Can: "
            + player.getWateringCan().getCurrentWtrLevel()
            + " / " + player.getWateringCan().getMaxWtrLevel());
        System.out.println("==========================================");
    }

    /**
     * Displays the 10x10 farm grid with color coding.
     * Yellow brackets = fertilized tile
     * Blue letter = watered plant
     * Green letter = unwatered plant
     * X = blocked by meteorite
     */
    private void displayFarm(){
        Field farm = player.getFarm();
        System.out.println("                FARM GRID               ");
        System.out.println("==========================================");

        // print column headers (0-9)
        System.out.print("    ");
        for(int c = 0; c < farm.getCols(); c++){
            System.out.print(" " + c + "  ");
        }
        System.out.println();

        // iterate through rows and columns
        for(int r = 0; r < farm.getRows(); r++){
            System.out.print(r + "   ");
            for(int c = 0; c < farm.getCols(); c++){
                Soil tile = farm.getTile(r, c);
                Plant plant = tile.getPlant();

                String lb = "[";
                String rb = "]";

                // change brackets to yellow if fertilizer is alive
                if(tile.hasFertilizer()){
                    lb = YELLOW + "[" + RESET;
                    rb = YELLOW + "]" + RESET;
                }

                if(tile.isBlocked()){
                    System.out.print(RED + "[X]" + RESET + " ");
                } else if(plant == null){
                    char soilIcon = tile.getName().toUpperCase().charAt(0);
                    System.out.print(lb + soilIcon + rb + " ");
                } else {
                    char icon = plant.getName().charAt(0);

                    // Uppercase if mature, lowercase if not
                    if (plant.isMature()){
                        icon = Character.toUpperCase(icon);
                    } else {
                        icon = Character.toLowerCase(icon);
                    }

                    // Apply color based if watered
                    if(plant.isWatered()){
                        System.out.print(lb + BLUE + icon + RESET + rb + " ");
                    } else {
                        System.out.print(lb + GREEN + icon + RESET + rb + " ");
                    }
                }
            }
            System.out.println();
        }
        // updated legend to explain growth stages and soil types
        System.out.println("==========================================");
        System.out.println("Legend: [L]=Loam  [S]=Sand  [G]=Gravel  [X]=blocked");
        System.out.println("Growth: [t]=Growing (Immature) [T]=Ready (Mature)");
        System.out.println(GREEN + "[P]" + RESET + "=unwatered  "
            + BLUE + "[P]" + RESET + "=watered  "
            + YELLOW + "[ ]" + RESET + "=fertilized");
    }

    /**
     * Handles planting a seed.
     * Shows a menu of affordable plants loaded from JSON,
     * then asks for tile coordinates.
     */
    private void plantSeed(){
        int selectedOption;
        String input = "";
        boolean isValidInput = false;
        int plantedCount = 0;

        System.out.println("\n===== PLANT MENU =====");

        // only show plants player can afford
        List<Plant> affordable = new ArrayList<>();
        for(Plant p : plants){
            if(player.canAfford((int)p.getSeedPrice())){
                affordable.add(p);
                System.out.println("[" + affordable.size() + "] "
                    + p.getName()
                    + " | Cost: " + p.getSeedPrice()
                    + " | Yield: " + p.getYield()
                    + " | Preferred model.Soil: " + p.getPreferredSoil());
            }
        }
        // if the list is empty, too broke to buy anything, exit
        if(affordable.isEmpty()){
            System.out.println("You can't afford any seeds!");
            return;
        }

        System.out.println("[0] Cancel");
        System.out.print("Choose a plant: ");
        String choice = scanner.nextLine().trim();
        if(choice.equals("0")) return;

        try {
            // converts whatever they inputted into a list position by subtracting 1 since zero based indexing.
            selectedOption = Integer.parseInt(choice) - 1;
        } catch(Exception e){
            System.out.println("Invalid choice!");
            return;
        }
        // check if what player inputted is actually one of the choices
        if(selectedOption < 0 || selectedOption >= affordable.size()){
            System.out.println("Invalid choice!");
            return;
        }

        Plant chosen = affordable.get(selectedOption);

        while (!isValidInput){
            System.out.println("\n Enter tile(s) to plant on (e.g. 0,0 or 0,0 1,1");
            System.out.println("[0] Cancel");
            System.out.println("Input: ");
            input = scanner.nextLine().trim();

            if (input.equals("0")){
                System.out.println("Cancelled. exiting now...");
                return;
            } else if (!input.contains(",")){
                // check for bad formatting (missing comma)
                System.out.println("Invalid format! please use row, col (e.g. 0,0)");
            } else {
                isValidInput = true;
            }
        }

        // splits the string of coordinates by spaces which gives us an array of individual coords
        String[] coords = input.split(" ");

        // we loop through every single tile inputted
        for (String target: coords){
            // just to check if they still have money for the target seed before we plant
            if (!player.canAfford((int)chosen.getSeedPrice())){
                System.out.println("Not enough money for the other seeds! skipping the rest..");
                break;
            }
            try {
                // we split the target by the comma to get the exact row and column number
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());

                Soil tile = player.getFarm().getTile(row, col);
                // makes sure the plant location is real, is empty, and not blocked
                if (tile == null || tile.isOccupied() || tile.isBlocked()){
                    System.out.println("Cannot plant at (" + target + "). Skipping..");
                    continue;
                }
                // create the new plant object from the stats of the chosen plant.
                Plant newPlant = new Plant(
                    chosen.getName(), chosen.getSellPrice(),
                    chosen.getSeedPrice(), chosen.getYield(),
                    chosen.getMaxGrowth(), chosen.getPreferredSoil());

                // plant/deduct money/ and increase the total counter of planted seeds
                tile.plantSeed(newPlant);
                player.deductSavings((int)chosen.getSeedPrice());
                plantedCount++;
                System.out.println("Planted " + chosen.getName() + " at (" + target + ")!");
            } catch (Exception e){
                // if they type a letter instead of number, the catch formats errors.
                System.out.println("Invalid coordinate: " + target + ". Skipping..");
            }
        }
        System.out.println("Successfully planted " + plantedCount + " seed(s)!");
    }

    /**
     * Handles watering one or multiple plants.
     * Input format: single tile (0,0) or multiple tiles (0,0 1,1 2,2)
     */
    private void waterPlant(){
        int wateredCount = 0;
        String input = "";
        boolean isValidInput = false;

        // check if the can is completely empty before even asking for coords.
        if(player.getWateringCan().isEmpty()){
            System.out.println("Watering can is empty! Refill first.");
            return;
        }

        while(!isValidInput){
            System.out.println("\nEnter tile(s) to water (e.g. 0,0 or 0,0 1,1)");
            System.out.println("[0] Cancel");
            System.out.print("Input: ");
            input = scanner.nextLine().trim();

            if(input.equals("0")){
                System.out.println("Cancelled.");
                return;
            } else if(!input.contains(",")){
                // missing comma, bad formatting
                System.out.println("Invalid format! Use row,col (e.g. 0,0)");
                continue;
            } else {
                isValidInput = true;
            }
        }
        // splits the string of coordinates by spaces which gives us an array of individual coords
        String[] coords = input.split(" ");

        // we loop through every single tile inputted
        for(String target : coords){
            // check if we ran out of water while processing the previous tiles
            if(player.getWateringCan().isEmpty()){
                System.out.println("Watering can empty! Stopping.");
                break;
            }
            try {
                // split the target by the comma to get the exact row and column numbers
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());

                Soil tile = player.getFarm().getTile(row, col);
                // make sure the dirt actually has a plant on it
                if(tile == null || !tile.isOccupied()){
                    System.out.println("No plant at (" + target + "). Skipping.");
                    continue;
                }
                // make sure the plant isn't already watered
                if(tile.getPlant().isWatered()){
                    System.out.println("Already watered at (" + target + "). Skipping.");
                    continue;
                }

                // apply water, deduct one level from the can
                tile.waterSoil();
                player.getWateringCan().useWater();
                wateredCount++;
                System.out.println("Watered " + tile.getPlant().getName()
                    + " at (" + target + ")!");

            } catch(Exception e){
                // if they type a letter instead of number, the catch formats errors.
                System.out.println("Invalid coordinate: " + target + ". Skipping.");
            }
        }
        System.out.println("Watered " + wateredCount + " plant(s).");
    }

    /**
     * Refills the watering can
     * Costs 100 savings and restores the can to its maximum capacity.
     */
    private void refillCan(){
        // check if the can is already full  so they don't accidentally waste their money
        if(player.getWateringCan().isFull()){
            System.out.println("Watering can is already full!");
            return;
        }
        // check if they have 100 savings required to buy a refill.
        if(!player.canAfford(100)){
            System.out.println("Not enough savings! Refill costs 100.");
            return;
        }
        // fill the can
        player.deductSavings(100);
        player.getWateringCan().refill();
        System.out.println("Watering can refilled!");
    }

    /**
     * Handles applying fertilizer to one or multiple tiles.
     * model.Fertilizer list is loaded from JSON.
     */
    private void applyFertilizer(){
        int selectedOption;
        int fertCount = 0;
        String input = "";
        boolean isValidInput = false;

        System.out.println("\n===== FERTILIZER MENU =====");

        // print all the fertilizers loaded from the JSON file
        for(int i = 0; i < fertilizers.size(); i++){
            Fertilizer f = fertilizers.get(i);
            System.out.println("[" + (i+1) + "] "
                + f.getName()
                + " | Price: " + f.getPrice()
                + " | Days: " + f.getEffectDays());
        }
        System.out.println("[0] Cancel");
        System.out.print("Choose a fertilizer: ");

        String choice = scanner.nextLine().trim();
        if(choice.equals("0")) return;


        try {
            // converts whatever they inputted into a list position by subtracting 1 since zero based indexing.
            selectedOption = Integer.parseInt(choice) - 1;
        } catch(Exception e){
            System.out.println("Invalid choice!");
            return;
        }
        // check to see if the choice is actually in the list
        if(selectedOption < 0 || selectedOption >= fertilizers.size()){
            System.out.println("Invalid choice!");
            return;
        }

        Fertilizer chosen = fertilizers.get(selectedOption);

        // check if they can't even afford one.
        if(!player.canAfford((int)chosen.getPrice())){
            System.out.println("Not enough money! "
                + chosen.getName() + " costs " + chosen.getPrice());
            return;
        }

        while(!isValidInput){
            System.out.println("\nEnter tile(s) to fertilize (e.g. 0,0 or 0,0 1,1)");
            System.out.println("[0] Cancel");
            System.out.print("Input: ");
            input = scanner.nextLine().trim();

            if(input.equals("0")){
                System.out.println("Cancelled.");
                return;
            } else if(!input.contains(",")){
                System.out.println("Invalid format! Use row,col (e.g. 0,0)");
                continue;
            } else {
                isValidInput = true;
            }
        }

        String[] coords = input.split(" ");

        for(String target : coords){
            // stop mid-list if tey run out of money
            if(!player.canAfford((int)chosen.getPrice())){
                System.out.println("Not enough money! Stopping.");
                break;
            }
            try {
                // split the target by the comma to get the exact row and column numbers
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());

                Soil tile = player.getFarm().getTile(row, col);

                // don't apply if tile is invalid or currently blocked
                if(tile == null || tile.isBlocked()){
                    System.out.println("Invalid tile at (" + target + "). Skipping.");
                    continue;
                }
                // don't apply fertilizer if it already has some
                if(tile.hasFertilizer()){
                    System.out.println("Already fertilized at (" + target + "). Skipping.");
                    continue;
                }

                // create fresh copy of fertilizer for each tile
                Fertilizer newFert = new Fertilizer(
                    chosen.getName(), chosen.getPrice(), chosen.getEffectDays()
                );
                // apply to soil / charge the player / update the fertilizer counter.
                tile.applyFertilizer(newFert);
                player.deductSavings((int)chosen.getPrice());
                fertCount++;
                System.out.println("Applied " + chosen.getName()
                    + " at (" + target + ")!");

            } catch(Exception e){
                // for formatting error like inputting entering letters
                System.out.println("Invalid coordinate: " + target + ". Skipping.");
            }
        }
        System.out.println("Fertilized " + fertCount + " tile(s).");
    }

    /**
     * Handles removing or harvesting plants from one or multiple tiles.
     * If plant is mature, it is harvested and sells the crop.
     * If not mature, it is removed and gives no reward.
     */
    private void removeOrHarvest(){
        String input = "";
        boolean isValidInput = false;

        while(!isValidInput){
            System.out.println("\nEnter tile(s) to remove/harvest (e.g. 0,0 or 0,0 1,1)");
            System.out.println("[0] Cancel");
            System.out.print("Input: ");
            input = scanner.nextLine().trim();

            if(input.equals("0")){
                System.out.println("Cancelled.");
                return;
            } else if(!input.contains(",")){
                // missing comma, bad formatting, keep looping
                System.out.println("Invalid format! Use row,col (e.g. 0,0)");
                continue;
            } else {
                isValidInput = true;
            }
        }

        // split the input by spaces to process each tile individually
        String[] coords = input.split(" ");

        for(String target : coords){
            try {
                // split the target by the comma to get the exact row and column numbers
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());

                Soil tile = player.getFarm().getTile(row, col);

                // checks if tile exists and actually has something growing on it.
                if(tile == null || !tile.isOccupied()){
                    System.out.println("No plant at (" + target + "). Skipping.");
                    continue;
                }

                String plantName = tile.getPlant().getName();

                // check its maturity status before removing it.
                boolean wasMature = tile.getPlant().isMature();
                float value = tile.removeOrHarvest();

                if(wasMature){
                    // if it was mature then add the profit
                    player.addSavings((int)value);
                    System.out.println("Harvested " + plantName
                        + " at (" + target + ")! Earned: " + value);
                } else {
                    // just deleted.
                    System.out.println("Removed " + plantName
                        + " at (" + target + ").");
                }

            } catch(Exception e){
                // for formatting error like inputting entering letters
                System.out.println("Invalid coordinate: " + target + ". Skipping.");
            }
        }
    }

    /**
     * Advances the game to the next day.
     * Triggers meteorite event at end of day 7.
     */
    private void nextDay(){

        player.nextDay();
        // meteorite hits at end of day 7 (start of day 8)
        if(player.getCurrentDay() == 8){
            triggerMeteoriteEvent();
        }
        System.out.println("\n========= Day " + player.getCurrentDay() + " begins! =========");
        System.out.println("\tYou earned 50 savings.");
        System.out.println("==========================================");
        displayFarm();
    }

    /**
     * Triggers the meteorite event.
     * If plants are on affected tiles, they are all removed/harvested.
     */
    private void triggerMeteoriteEvent(){
        System.out.println("\n***** A METEORITE HAS HIT THE FIELD! *****");
        System.out.println("Some tiles have been blocked!!");

        // affected tiles based on specs figure
        int[][] affected = {
                {3,3},{3,4},{3,5},{3,6}, {4,3},{4,4},{4,5},{4,6},
                {5,3},{5,4},{5,5},{5,6}, {6,3},{6,4},{6,5},{6,6},
                {1,1},{1,4},{1,5},{1,8}, {4,1},{5,1},{4,8},{5,8},
                {8,1},{8,4},{8,5},{8,8}
        };

        // iterate through each of the affected tiles
        for(int[] coord : affected) {
            // get the soil tile from the 10x10 farm grid
            Soil s = player.getFarm().getTile(coord[0], coord[1]);
            if (s != null) {
                // so it doesn't crash when it checks an empty tile.
                if (s.isOccupied()){
                    // check the maturity before clearing
                    boolean wasMature = s.getPlant().isMature();
                    // clears the plant + return value.
                    float value = s.removeOrHarvest();

                    if (wasMature) {
                        // if fully grown then add the profit to player's savings.
                        player.addSavings((int) value);
                        System.out.println("model.Plant at (" + coord[0] + "," + coord[1] + ") was matured and harvested!");
                    } else {
                        // immature plants are destroyed completely.
                        System.out.println("model.Plant at (" + coord[0] + "," + coord[1] + ") was destroyed!");
                    }
                }
                // flag it as blocked until it gets excavated.
                s.setBlocked(true);
            }
        }
    }

    /**
     * Handles the excavation of tiles blocked by the meteorite event.
     * Prompts the player for tile coordinates to unblock. Each excavation
     * costs 500 savings, removes the blocked status, applies a permanent
     * "Stardust" fertilizer, and counts towards the daily limit of 5.
     *
     */
    private void excavateMeteorite(){
        String input = "";


        // prevent if 5 excavations already
        if (player.getExcavatedToday() >= 5){
            System.out.println("You have already excavated 5 times today.");
            return;
        }
        // check for when they can't afford
        if (!player.canAfford(500)){
            System.out.println("You don't have enough money!");
            return;
        }

        while (true) {
            System.out.println("\nEnter tile(s) to excavate (e.g. 0,0 or 0,0 1,1)");
            System.out.println("[0] Cancel");
            System.out.print("Input: ");
            input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("Excavation cancelled. Returning to Main Menu.");
                return;
            }

            // missing comma inputs
            if (!input.contains(",")) {
                System.out.println("Invalid format! Use row,col (e.g. 0,0)");
                continue;
            }
            break;
        }
        // split the input into array of coords ex: ["0,0", "1,1"]
        String[] coords = input.split(" ");

        // 6. loop 1by1 through the list of tiles
        for (String target : coords) {

            // Stop processing the rest of the list if limit is reached
            if (player.getExcavatedToday() >= 5) {
                System.out.println("Daily excavation limit (5) reached! Stopping early.");
                break;
            }

            // Stop processing the rest of the list if the player runs out of money
            if (!player.canAfford(500)) {
                System.out.println("Not enough money for the rest of the tiles! Stopping early.");
                break;
            }

            try {
                // splits the text at the comma, converts it into usable grid nums.
                String[] parts = target.split(",");
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());

                Soil tile = player.getFarm().getTile(row, col);

                // VALIDATION: checks if tile is blocked by meteorite
                // skipped if normal tile, move to next in list.
                if (tile == null || !tile.isBlocked()) {
                    System.out.println("Tile at (" + target + ") is not blocked by a meteorite. Skipping.");
                    continue;
                }

                // Deduct, unblock tile, apply permanent fertilizer
                player.deductSavings(500);
                tile.setBlocked(false);
                tile.setFertilizerPermanent(true);

                // Add a permanent "Stardust" fertilizer
                tile.applyFertilizer(new Fertilizer("Stardust", 0, 9999));

                // Increment our daily tracker
                player.addExcavationCounter();
                System.out.println("Successfully excavated (" + target + ")! It is now permanently fertilized.");

            } catch (Exception e) {
                // Catches bad formatting like typing letters instead of numbers
                System.out.println("Invalid coordinate: " + target + ". Skipping.");
            }
        }
    }
}