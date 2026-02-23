import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        WateringCan waterCan = new WateringCan();

        Player myPlayer = new Player("FarmerTest", 1000, waterCan);
         Controller game = new Controller(myPlayer, scanner);
         game.startGame();

    }
}
