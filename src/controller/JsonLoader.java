package controller;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class handles all the file reading and writing for the farming simulator
 * uses the GSON library to parse JSON files and load the available plants,fertilizers
 * starting map layout and saving and loading player's high scores.
 */
public class JsonLoader {

    /**
     * method that reads the plant data from a JSON file and
     * creates a list of model.Plant objects to be used in the game.
     *
     * @param filename is the path to the JSON file containing the plant data
     * @return a list of fully initialized model.Plant objects
     */
    public static List<Plant> loadPlants(String filename) {
        List<Plant> plants = new ArrayList<>();

        try (Reader reader = new FileReader(filename)) {
            // parse the entire text file into a JSON object
            JsonObject entireJsonFile = JsonParser.parseReader(reader).getAsJsonObject();

            // for every plant (turnip, carrot etc.)
            for (String plantId : entireJsonFile.keySet()) {
                // get the specific JSON object that holds the stats for the specific plant
                JsonObject plantInfo = entireJsonFile.getAsJsonObject(plantId);
                // turn the values into java data types
                String name     = plantInfo.get("name").getAsString();
                float seedPrice = plantInfo.get("price").getAsFloat();
                float cropPrice = plantInfo.get("crop_price").getAsFloat();
                int yield       = plantInfo.get("yield").getAsInt();
                int maxGrowth   = plantInfo.get("max_growth").getAsInt();
                String soil     = plantInfo.get("preferred_soil").getAsString();
                // build the new model.Plant object with all the information
                plants.add(new Plant(name, cropPrice, seedPrice, yield, maxGrowth, soil));
            }
        } catch (Exception e) {
            System.out.println("Error loading plants: " + e.getMessage());
        }
        return plants;
    }

    /**
     * method that reads the fertilizer data from a JSON file and
     * creates a list of model.Fertilizer objects to be used in the game.
     *
     * @param filename is the path to the JSON containing fertilizer data
     * @return a list of fully initialized fertilizer objects
     */
    public static List<Fertilizer> loadFertilizers(String filename) {
        List<Fertilizer> fertilizers = new ArrayList<>();

        try (Reader reader = new FileReader(filename)) {
            // parse the entire text file into a JSON object
            JsonObject entireJsonFile = JsonParser.parseReader(reader).getAsJsonObject();
            // for every fertilizer in the file
            for (String fertilizerId : entireJsonFile.keySet()) {
                // get the JSON object that holds the stat for fertilizer
                JsonObject fertilizerData = entireJsonFile.getAsJsonObject(fertilizerId);
                // turn the values into java data types
                String name = fertilizerData.get("name").getAsString();
                float price = fertilizerData.get("price").getAsFloat();
                int days    = fertilizerData.get("days").getAsInt();
                // build the new fertilizer object with all the information
                fertilizers.add(new Fertilizer(name, price, days));
            }
        } catch (Exception e) {
            System.out.println("Error loading fertilizers: " + e.getMessage());
        }
        return fertilizers;
    }

    /**
     * method that translates the Map JSON file into the 10x10 model.Field grid.
     * it uses the file's legend to match the letters to the real soil names
     * then replaces the starting "blank" tiles with the actual soil
     *
     * @param filename path to the JSON file containing the map layout
     * @param field is the object that will have its blank tiles updated
     */
    public static void loadMap(String filename, Field field) {

        try (Reader reader = new FileReader(filename)) {
            // Parse the JSON file into a JSON object
            JsonObject entireJsonFile = JsonParser.parseReader(reader).getAsJsonObject();
            // get the legend and the 2d array "map" from the JSON file
            JsonObject legend = entireJsonFile.getAsJsonObject("legend");
            JsonArray map     = entireJsonFile.getAsJsonArray("map");

            // use a hashmap data struct to be our "translator"
            // Mapping: "l" to "loam", "s" to "sand", "g" to "gravel"
            Map<String, String> letterToName = new HashMap<>();
            for (String soilName : legend.keySet()) {
                String soilLetter = legend.get(soilName).getAsString();
                letterToName.put(soilLetter, soilName);
            }

            // loop through rows and columns of the 2D array
            for (int r = 0; r < map.size(); r++) {
                JsonArray row = map.get(r).getAsJsonArray();
                for (int c = 0; c < row.size(); c++) {
                    String soilLetter = row.get(c).getAsString();
                    // look up the full name of the soil using the hashmap
                    String soilName = letterToName.get(soilLetter);
                    // if the letter is valid then we change the field's blank tile with the real soil object
                    if (soilName != null) {
                        field.setTile(r, c, new Soil(soilName));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading map: " + e.getMessage());
        }
    }

    /**
     * method that reads past high scores from a JSON file
     * and loads them into the controller.HighScoreManager
     *
     * @param filename path to the JSON file containing the high scores
     * @return a controller.HighScoreManager object with the saved scores
     */
    public static HighScoreManager loadHighScores(String filename) {
        HighScoreManager manager = new HighScoreManager();
        try (Reader reader = new FileReader(filename)) {
            // Parse the JSON file into a JSON object
            JsonObject entireJsonFile = JsonParser.parseReader(reader).getAsJsonObject();
            // loop through all the score entries in the file
            for (String score : entireJsonFile.keySet()) {
                // get the JSON object holding the stats for the high score
                JsonObject entry = entireJsonFile.getAsJsonObject(score);
                // get the player's name and their final savings
                String name  = entry.get("name").getAsString();
                int savings  = entry.get("savings").getAsInt();
                // add the score into the manager
                manager.addScore(name, savings);
            }
        } catch (Exception e) {
            // if file doesn't exist then no one has played yet before.
            System.out.println("Note: Starting with fresh high scores.");
        }
        return manager;
    }

    /**
     * method that saves the current list of high scores back out to the JSON file
     * writes the updated data.
     *
     * @param filename is the path to the JSON file where it everything is saved
     * @param manager the manager which contains the list of scores
     */
    public static void saveHighScores(String filename, HighScoreManager manager) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Create an empty JSON object which is the container for the scores
            JsonObject entireJsonFile = new JsonObject();
            List<HighScore> scores = manager.getScores();
            // go through all the high scores saved in the list
            for (int i = 0; i < scores.size(); i++) {
                // create a JSON object just for the current player
                JsonObject entry = new JsonObject();// put the player name and savings amount into the object
                entry.addProperty("name", scores.get(i).getPlayerName());
                entry.addProperty("savings", scores.get(i).getSavings());
                // add the player's object to the "container" and then we use the rank as label
                entireJsonFile.add(String.valueOf(i + 1), entry);
            }
            // we convert the container into readable text and then it's saved to the actual file
            new GsonBuilder().setPrettyPrinting().create().toJson(entireJsonFile, writer);
        } catch (Exception e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }


}