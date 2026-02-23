import java.util.*;


public class Controller {
    private Player player;
    private Scanner scanner;
    private boolean isRunning;

    public Controller(Player player, Scanner scanner){
        this.player = player;
        this.scanner = scanner;
        this.isRunning = true;
    }

    /**
     *
     *
     */

    public void startGame(){
        while (isRunning && player.getCurrentDay() <= 15){

            displayGame();
            displayFarm();
            System.out.print("type 'quit' to exit: ");
            String input = scanner.nextLine();
            if (input.equals("quit")){
                this.isRunning = false;
            }
        }
        System.out.println("\n==========================================");
        System.out.println("   Season Ended! Final Savings: " + player.getSavings());
        System.out.println("==========================================");
    }

    /**
     *
     *
     */

    private void displayGame(){
        System.out.println("\n==========================================");
        System.out.println("               VERDANT SUN               ");
        System.out.println("\n==========================================");
        System.out.println(" Day: " + player.getCurrentDay() + " Savings: " + player.getSavings() + "\n");
        System.out.println(" Water Can Level: " + player.getWateringCan().getCurrentWtrLevel() + " / " + player.getWateringCan().getMaxWtrLevel() + "\n");
        System.out.println("\n==========================================");
    }

    /**
     *
     *
     */

    private void displayFarm() {
        Field farm = player.getFarm();
        System.out.println("               FARM GRID               ");
        System.out.println("==========================================");

        // Print Column Headers (0 to 9) at the very top
        System.out.print("   ");
        for (int c = 0; c < farm.getCols(); c++) {
            System.out.print(" " + c + "  ");
        }
        System.out.println();


        for (int r = 0; r < farm.getRows(); r++) {
            System.out.print(r + "  ");

            // Loop through every column in this row
            for (int c = 0; c < farm.getCols(); c++) {
                Soil currentTile = farm.getTile(r, c);
                Plant currentPlant = currentTile.getPlant();

                // Check what is currently on the tile (IGNORE THIS FOR NOW)
                if (currentTile.isBlocked()) {
                    System.out.print("[X] "); // Meteorite
                } else if (currentPlant == null) {
                    System.out.print("[-] "); // Empty soil
                } else {
                    char plantIcon = currentPlant.getName().charAt(0); // gets first letter of plant for the icon on grid
                    System.out.print("[" + plantIcon + "] ");
                }
            }
            System.out.println(); // Jump to the next line after finishing a row
        }
        System.out.println("==========================================");
    }


}
