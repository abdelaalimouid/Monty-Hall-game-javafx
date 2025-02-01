package model;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class DoorFactory {
    public static List<Door> createDoors() {
        List<Door> doors = Arrays.asList(new EmptyDoor(), new EmptyDoor(), new PrizeDoor());
        Collections.shuffle(doors);
        return doors;
    }
}