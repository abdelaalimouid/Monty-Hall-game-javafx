package game;

import factory.DoorFactory;
import host.Host;
import model.Door;
import observer.GameObserver;
import state.GameState;
import strategy.Strategy;

import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<GameObserver> observers = new ArrayList<>();
    private List<Door> doors;
    private int userChoice = -1;
    private int finalChoice = -1;
    private Strategy strategy;
    private final DoorFactory doorFactory;
    private GameState currentState;

    public Game(DoorFactory doorFactory) {
        this.doorFactory = doorFactory;
        resetGame();
    }

    public void resetGame() {
        doors = doorFactory.createDoors();
        userChoice = -1;
        finalChoice = -1;
        currentState = new state.WaitingForChoiceState();
        notifyObservers("Game reset. Choose a door to start.");
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public int chooseDoor(int choice) {
        if (choice < 0 || choice >= doors.size()) {
            throw new IllegalArgumentException("Invalid door choice.");
        }
        this.userChoice = choice;
        notifyObservers("You chose door " + (choice + 1) + ".");
        int revealedDoor = Host.getInstance().revealEmptyDoor(doors, userChoice);
        notifyObservers("Host opened door " + (revealedDoor + 1) + ", which is empty.");
        setState(new state.FinalizingChoiceState());
        return revealedDoor;
    }

    public void finalizeChoice() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set. Cannot finalize choice.");
        }
        Door finalDoor = strategy.chooseDoor(doors, userChoice);
        finalChoice = doors.indexOf(finalDoor);
        if (finalDoor.hasPrize()) {
            notifyObservers("Congratulations! You won!");
        } else {
            notifyObservers("Sorry, you lost.");
        }
    }

    public int getFinalChoice() {
        return finalChoice;
    }

    public int getWinningDoor() {
        for (int i = 0; i < doors.size(); i++) {
            if (doors.get(i).hasPrize()) {
                return i;
            }
        }
        throw new IllegalStateException("No door contains the prize.");
    }

    public List<Door> getDoors() {
        return doors;
    }

    public int getUserChoice() {
        return userChoice;
    }

    private void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.update(message);
        }
    }

    public void handleInput(int input) {
        currentState.handleInput(this, input);
    }
}
