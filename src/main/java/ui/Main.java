package ui;

import factory.MontyHallDoorFactory;
import game.Game;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import strategy.StayStrategy;
import strategy.SwitchStrategy;

import java.util.Objects;

public class Main extends Application {
    private Game game;
    private Label messageLabel;
    private HBox doorsBox;
    private VBox switchContainer;
    private Button hintButton;
    private Button switchButton;
    private Button stayButton;
    private int hostRevealedDoor = -1;

    @Override
    public void start(Stage primaryStage) {
        VBox introScreen = new VBox(15);
        introScreen.setAlignment(Pos.CENTER);
        introScreen.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 20px;");

        Label introLabel = new Label("""
                Welcome to the Monty Hall Game!
                
                Choose one of three doors. The host will reveal an empty door,
                and you will then have the option to stay or switch.
                Hint: Statistically, switching increases your chance of winning.""");
        introLabel.setTextFill(Color.WHITE);
        introLabel.setStyle("-fx-font-size: 16px;");

        Button playButton = new Button("Play");
        playButton.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white;");
        playButton.setOnAction(e -> showGameScreen(primaryStage));

        introScreen.getChildren().addAll(introLabel, playButton);
        primaryStage.setScene(new Scene(introScreen, 600, 400));
        primaryStage.setTitle("Monty Hall Game");
        primaryStage.show();
    }

    private void showGameScreen(Stage primaryStage) {
        game = new Game(new MontyHallDoorFactory());
        game.addObserver(message -> updateMessage(message));

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 20px;");

        messageLabel = new Label("Choose a door to start the game.");
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setStyle("-fx-font-size: 18px;");

        doorsBox = new HBox(20);
        doorsBox.setAlignment(Pos.CENTER);
        createDoorButtons();

        switchContainer = new VBox(10);
        switchContainer.setAlignment(Pos.CENTER);

        hintButton = new Button("Show Probability Hint");
        hintButton.setStyle("-fx-font-size: 14px; -fx-background-color: #555; -fx-text-fill: white;");
        hintButton.setOnAction(e -> showHint());

        Button resetButton = new Button("Restart Game");
        resetButton.setStyle("-fx-font-size: 14px; -fx-background-color: #c0392b; -fx-text-fill: white;");
        resetButton.setOnAction(e -> resetGame());

        HBox buttonBox = new HBox(10, hintButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(messageLabel, doorsBox, switchContainer, buttonBox);
        primaryStage.setScene(new Scene(root, 600, 400));
    }

    private void createDoorButtons() {
        doorsBox.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            VBox doorContainer = new VBox();
            doorContainer.setAlignment(Pos.CENTER);

            Image doorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/door.png")));
            ImageView doorView = new ImageView(doorImage);
            doorView.setFitWidth(150);
            doorView.setFitHeight(150);

            final int doorIndex = i;
            doorView.setOnMouseClicked(e -> handleDoorSelection(doorIndex));
            doorContainer.getChildren().add(doorView);
            doorsBox.getChildren().add(doorContainer);
        }
    }

    private void handleDoorSelection(int choice) {
        if (game.getUserChoice() == -1) {
            highlightDoor(choice, "blue");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                hostRevealedDoor = game.chooseDoor(choice);

                VBox hostDoorContainer = (VBox) doorsBox.getChildren().get(hostRevealedDoor);
                ImageView hostDoorView = (ImageView) hostDoorContainer.getChildren().get(0);
                Image goatImage = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/images/goat.png"),
                        "Goat image not found"));
                hostDoorView.setImage(goatImage);

                highlightDoor(hostRevealedDoor, "gray");
                updateMessage("Host opened door " + (hostRevealedDoor + 1) + ". Do you want to switch?");
                showSwitchOptions();
            });
            pause.play();
        }
    }

    private void highlightDoor(int doorIndex, String color) {
        VBox doorContainer = (VBox) doorsBox.getChildren().get(doorIndex);
        doorContainer.setStyle("-fx-border-color: " + color + "; -fx-border-width: 3px;");
    }

    private void showSwitchOptions() {
        switchContainer.getChildren().clear();

        switchButton = new Button("Switch");
        switchButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        switchButton.setOnAction(e -> handleFinalChoice(new SwitchStrategy()));

        stayButton = new Button("Stay");
        stayButton.setStyle("-fx-background-color: #d35400; -fx-text-fill: white;");
        stayButton.setOnAction(e -> handleFinalChoice(new StayStrategy()));

        HBox switchBox = new HBox(10, switchButton, stayButton);
        switchBox.setAlignment(Pos.CENTER);
        switchContainer.getChildren().add(switchBox);
    }

    private void handleFinalChoice(strategy.Strategy strategy) {
        game.setStrategy(strategy);
        game.finalizeChoice();
        revealFinalChoice();
        hintButton.setDisable(true);
        switchButton.setDisable(true);
        stayButton.setDisable(true);
    }

    private void revealFinalChoice() {
        int finalUserChoice = game.getFinalChoice();
        int winningDoor = game.getWinningDoor();

        VBox chosenContainer = (VBox) doorsBox.getChildren().get(finalUserChoice);
        ImageView chosenView = (ImageView) chosenContainer.getChildren().get(0);
        Image chosenImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                finalUserChoice == winningDoor ? "/images/car.png" : "/images/goat.png"
        )));
        chosenView.setImage(chosenImage);
        if (finalUserChoice == winningDoor) {
            chosenContainer.setStyle("-fx-border-color: green; -fx-border-width: 3px;");
        } else {
            chosenContainer.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            for (int i = 0; i < 3; i++) {
                if (i == finalUserChoice) {
                    continue;
                }
                VBox doorContainer = (VBox) doorsBox.getChildren().get(i);
                ImageView doorView = (ImageView) doorContainer.getChildren().get(0);
                Image resultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                        i == winningDoor ? "/images/car.png" : "/images/goat.png"
                )));
                doorView.setImage(resultImage);
                if (i == winningDoor) {
                    doorContainer.setStyle("-fx-border-color: green; -fx-border-width: 3px;");
                } else {
                    doorContainer.setStyle("");
                }
            }
            updateMessage(finalUserChoice == winningDoor ?
                    "Congratulations, you won!" :
                    "Sorry, you lost. The prize was behind door " + (winningDoor + 1) + ".");
        });
        pause.play();
    }

    private void showHint() {
        if (!messageLabel.getText().contains("2/3 chance")) {
            updateMessage("Hint: The unopened door has a 2/3 chance of hiding the prize.");
        } else {
            updateMessage("");
        }
    }

    private void updateMessage(String message) {
        messageLabel.setText(message);
    }

    private void resetGame() {
        game.resetGame();
        updateMessage("Choose a door to start the game.");
        doorsBox.getChildren().clear();
        createDoorButtons();
        switchContainer.getChildren().clear();
        hintButton.setDisable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
