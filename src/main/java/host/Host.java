package host;

import model.Door;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Host {
    private static Host instance = new Host();
    private Random random = new Random();

    private Host() {}

    public static Host getInstance() {
        return instance;
    }

    public int revealEmptyDoor(List<Door> doors, int userChoice) {
        List<Integer> validChoices = new ArrayList<>();
        for (int i = 0; i < doors.size(); i++) {
            if (i != userChoice && !doors.get(i).hasPrize()) {
                validChoices.add(i);
            }
        }
        int choice = validChoices.get(random.nextInt(validChoices.size()));
        doors.get(choice).open();
        return choice;
    }
}
