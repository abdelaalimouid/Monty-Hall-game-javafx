package strategy;

import model.Door;
import java.util.List;

public interface Strategy {
    Door chooseDoor(List<Door> doors, int initialChoice);
}