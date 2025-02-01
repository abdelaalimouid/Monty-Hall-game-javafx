package game;

import host.Host;
import model.Door;
import model.DoorFactory;
import observer.GameObserver;
import strategy.Strategy;

import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<GameObserver> observers = new ArrayList<>();
    private List<Door> doors;
    private int userChoice;
    private Strategy strategy;

    public Game() {
        resetGame();
    }

    public void resetGame() {
        doors = DoorFactory.createDoors();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int chooseDoor(int choice) {
        this.userChoice = choice;
        notifyObservers("You chose door " + (choice + 1));

        int hostOpenedDoor = Host.getInstance().revealEmptyDoor(doors, userChoice);
        return hostOpenedDoor;
    }
    public int getWinningDoor() {
        for (int i = 0; i < doors.size(); i++) {
            if (doors.get(i).hasPrize()) {
                return i;
            }
        }
        throw new IllegalStateException("No door has a prize. This should not happen.");
    }


    public void finalizeChoice() {
        Door finalDoor = strategy.chooseDoor(doors, userChoice);
        if (finalDoor.hasPrize()) {
            notifyObservers("You won!");
        } else {
            notifyObservers("You lost!");
        }
    }

    private void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.update(message);
        }
    }
}