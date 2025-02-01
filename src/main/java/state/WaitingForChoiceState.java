package state;

import game.Game;

public class WaitingForChoiceState implements GameState {
    @Override
    public void handleInput(Game game, int input) {
        game.chooseDoor(input);
    }
}