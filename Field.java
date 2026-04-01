/**
 *  this class represents the main 10x10 farm grid where the player plants crops
 *  it manages the layout of individual soil tiles and handles updating the
 *  entire field when advancing to the next day
 *
 */

public class Field {
    private final int ROWS = 10;
    private final int COLS = 10;

    private Soil[][] tiles;

    /**
     * This is the constructor that creates an instance of the Field class.
     * It intiializes a 10x10 grid with placeholder "blank" soil tiles,
     * which are overwritten when map data is loaded.
     *
     */

    public Field (){
        this.tiles = new Soil[ROWS][COLS];
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                this.tiles[i][j] = new Soil("blank"); // default, it will be changed.
            }
        }
    }

    /**
     * Checks if the given row and column indices are within the valid bounds of the field.
     * Should be called before accessing any tile to prevent index out of bounds errors.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if the tile exists within the 10x10 grid, false otherwise
     */
    public boolean isTileValid(int row, int col){
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /**
     * Advances all tiles in the field to the next day by iterating
     * through the grid and updating every individual tile.
     */
    public void nextDay(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tiles[i][j].nextDay();
            }
        }
    }

    /**
     * Returns the number of rows in the field
     *
     * @return rows as an integer
     */

    public int getRows(){
        return ROWS;
    }

    /**
     * Returns the number of columns in the field
     *
     * @return columns as an integer
     */

    public int getCols() {
        return COLS;
    }

    /**
     * Returns the soil object at a specific tile location.
     *
     * @param row the row index
     * @param col the col index
     * @return the Soil object at the specified location
     */

    public Soil getTile(int row, int col){
        if (isTileValid(row, col)){
            return tiles[row][col];
        }
        return null;
    }

    /**
     * Sets the soil object at a specific tile location.
     *
     * @param row the row index
     * @param col the col index
     * @param soil the Soil object to set at the specified location
     */

    public void setTile(int row, int col, Soil soil){
        this.tiles[row][col] = soil;
    }

    /**
     * Returns the entire grid of tiles.
     *
     * @return 2D array of Soil objects.
     */

    public Soil[][] getTiles(){
        return tiles;
    }

}
