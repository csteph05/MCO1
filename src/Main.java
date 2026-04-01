import controller.Controller;
import controller.HighScoreManager;
import controller.JsonLoader;
import model.Fertilizer;
import model.Plant;
import model.Player;
import model.WateringCan;

import java.util.*;

/**
 * Main class that serves as the entry point for the VerdantSun farming simulator.
 */
public class Main {

    /**
     * main method that launches the application when called
     * prompts the user for their name, initializes the player and starting items,
     * loads all game data from JSON files.
     *
     * @param args accepts the instructions/arguments from the CLI.
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // initial player object and watering can.
        WateringCan waterCan = new WateringCan();
        Player myPlayer = new Player(name, 1000, waterCan);

        // loads all the game items and past high scores from their respective JSON files
        // which are stored in lists so the game can easily look it up.
        JsonLoader.loadMap("Map.json", myPlayer.getFarm());
        List<Plant> plants = JsonLoader.loadPlants("Plants.json");
        List<Fertilizer> fertilizers = JsonLoader.loadFertilizers("Fertilizers.json");
        HighScoreManager hsManager = JsonLoader.loadHighScores("HighScores.json");

        // all loaded data is passed into the controller
        Controller game = new Controller(myPlayer, scanner,
                                         plants, fertilizers, hsManager);
        game.startGame();
    }
}