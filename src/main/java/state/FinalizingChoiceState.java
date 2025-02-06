package state;

import game.Game;

public class FinalizingChoiceState implements GameState {
    @Override
    public void handleInput(Game game, int input) {
        game.finalizeChoice();
    }
}
