package strategy;

import model.Door;
import java.util.List;

public class StayStrategy implements Strategy {
    @Override
    public Door chooseDoor(List<Door> doors, int initialChoice) {
        return doors.get(initialChoice);
    }
}