package state;

import game.Game;

public interface GameState {
    void handleInput(Game game, int input);
}