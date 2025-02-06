package factory;

import model.Door;
import model.EmptyDoor;
import model.PrizeDoor;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class MontyHallDoorFactory implements DoorFactory {
    @Override
    public List<Door> createDoors() {
        List<Door> doors = Arrays.asList(new EmptyDoor(), new EmptyDoor(), new PrizeDoor());
        Collections.shuffle(doors);
        return doors;
    }
}

