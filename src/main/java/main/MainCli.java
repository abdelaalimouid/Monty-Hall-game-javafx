package main;

import factory.MontyHallDoorFactory;
import game.Game;
import observer.ConsoleNotifier;
import strategy.SwitchStrategy;
import strategy.StayStrategy;

import java.util.Scanner;

public class MainCli {

    public static final String ANSI_RESET  = "\033[0m";
    public static final String ANSI_GREEN  = "\033[32m";
    public static final String ANSI_RED    = "\033[31m";
    public static final String ANSI_BLUE   = "\033[34m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            Game game = new Game(new MontyHallDoorFactory());
            game.addObserver(new ConsoleNotifier());

            System.out.println("\nWelcome to the Monty Hall Game!");
            System.out.println("There are 3 doors. Behind one door is a prize, and behind the others, nothing.");

            printDoors(game, -1, -1, -1);

            System.out.print("Choose a door (1, 2, or 3): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next();
                continue;
            }
            int userChoice = scanner.nextInt() - 1;
            if (userChoice < 0 || userChoice > 2) {
                System.out.println("Invalid door number. Please enter 1, 2, or 3.");
                continue;
            }

            int hostOpenedDoor = game.chooseDoor(userChoice);
            System.out.println("Probability Hint: Your current door has a 1/3 chance, and the remaining unopened door has a 2/3 chance.");

            printDoors(game, -1, hostOpenedDoor, -1);

            System.out.print("Do you want to switch your choice? (yes/no): ");
            String switchChoice = scanner.next().toLowerCase().trim();

            if (switchChoice.equals("yes")) {
                game.setStrategy(new SwitchStrategy());
            } else if (switchChoice.equals("no")) {
                game.setStrategy(new StayStrategy());
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                continue;
            }

            game.finalizeChoice();

            int finalChoice = game.getFinalChoice();
            int winningDoor = game.getWinningDoor();

            printDoors(game, finalChoice, hostOpenedDoor, winningDoor);

            System.out.println();
            System.out.print("Your final chosen door is " + (finalChoice + 1) + ". ");
            if (finalChoice == winningDoor) {
                System.out.println(ANSI_GREEN + "Congratulations, you won the prize!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Sorry, you lost. The prize was behind door " + (winningDoor + 1) + "." + ANSI_RESET);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainChoice = scanner.next().toLowerCase().trim();
            while (!playAgainChoice.equals("yes") && !playAgainChoice.equals("no")) {
                System.out.print("Do you want to play again? (yes/no): ");
                playAgainChoice = scanner.next().toLowerCase().trim();
            }
            if (!playAgainChoice.equals("yes")) {
                playAgain = false;
            }
        }

        System.out.println("Thanks for playing! Goodbye!");
        scanner.close();
    }

    private static void printDoors(Game game, int finalChoice, int hostDoor, int winningDoor) {
        int userChoice = game.getUserChoice();

        for (int i = 0; i < 3; i++) {
            String label;

            if (game.getDoors().get(i).isOpen()) {
                label = game.getDoors().get(i).hasPrize() ? "CAR" : "GOAT";
            } else {
                label = "DOOR";
            }

            String doorLine = "Door " + (i + 1) + ": [" + label + "]";

            if (i == userChoice && finalChoice == -1) {
                doorLine += " (Selected)";
            }
            if (i == hostDoor) {
                doorLine += " (Host opened)";
            }

            if (finalChoice != -1) {
                if (i == finalChoice && i == winningDoor) {
                    doorLine = ANSI_GREEN + doorLine + ANSI_RESET;
                } else if (i == finalChoice && i != winningDoor) {
                    doorLine = ANSI_RED + doorLine + ANSI_RESET;
                } else if (i == winningDoor) {
                    doorLine = ANSI_GREEN + doorLine + ANSI_RESET;
                }
            } else if (i == userChoice) {
                doorLine = ANSI_BLUE + doorLine + ANSI_RESET;
            }

            System.out.println(doorLine);
        }
        System.out.println();
    }
}
