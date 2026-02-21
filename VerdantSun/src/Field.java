public class Field {
    private final int rows = 10;
    private final int cols = 10;

    private Soil[][] tiles;

    /**
     * Constructor for creating a default 10x10 field
     * Each tile is initialized with a default Soil object of type "blank"
     */
    public Field (){
        this.tiles = new Soil[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.tiles[i][j] = new Soil("blank"); // default lang to, will be changed
            }
        }
    }

    /**
     * Returns the number of rows in the field
     *
     * @return rows as an integer
     */
    public int getRows(){
        return rows;
    }

    /**
     * Returns the number of columns in the field
     *
     * @return columns as an integer
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the soil object at a specific tile location.
     *
     * @param row the row index
     * @param col the col index
     * @return the Soil object at the specified location
     */
    public Soil getTile(int row, int col){
        return tiles[row][col];
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
