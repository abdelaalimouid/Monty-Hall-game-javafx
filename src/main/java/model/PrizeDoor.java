package model;

public class PrizeDoor implements Door {
    private boolean open = false;

    @Override
    public boolean hasPrize() {
        return true;
    }

    @Override
    public void open() {
        open = true;
    }

    @Override
    public boolean isOpen() {
        return open;
    }
}
