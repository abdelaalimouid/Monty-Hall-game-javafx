package observer;

public class ConsoleNotifier implements GameObserver {
    @Override
    public void update(String message) {
        System.out.println("Game Notification: " + message);
    }
}
