package model;

public class EmptyDoor implements Door {
    private boolean open = false;

    @Override
    public boolean hasPrize() {
        return false;
    }

    @Override
    public void open() {
        this.open = true;
    }

    @Override
    public boolean isOpen() {
        return open;
    }
}