package observer;

public class ProbabilityDisplayDecorator implements GameObserver {
    private GameObserver wrappedObserver;

    public ProbabilityDisplayDecorator(GameObserver wrappedObserver) {
        this.wrappedObserver = wrappedObserver;
    }

    @Override
    public void update(String message) {
        wrappedObserver.update(message);
    }
}
