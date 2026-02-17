public class Field {
    private final int rows = 10;
    private final int cols = 10;

    private Soil[][] tiles;

    // constructor for the 10x10 field
    public Field (){
        this.tiles = new Soil[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.tiles[i][j] = new Soil("Loam"); // default lang to, will be changed
            }
        }
    }
}
