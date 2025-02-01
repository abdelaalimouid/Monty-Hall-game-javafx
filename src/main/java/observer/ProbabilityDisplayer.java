package observer;

public class ProbabilityDisplayer implements GameObserver {
    private GameObserver observer;

    public ProbabilityDisplayer(GameObserver wrappedObserver) {
        this.observer = wrappedObserver;
    }

    @Override
    public void update(String message) {
        observer.update(message);
    }
}
