package strategy;

import model.Door;
import java.util.List;

public class SwitchStrategy implements Strategy {
    @Override
    public Door chooseDoor(List<Door> doors, int initialChoice) {
        for (int i = 0; i < doors.size(); i++) {
            if (i != initialChoice && !doors.get(i).isOpen()) {
                return doors.get(i);
            }
        }
        throw new IllegalStateException("No valid door available to switch.");
    }
}
