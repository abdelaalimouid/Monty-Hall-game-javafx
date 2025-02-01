package main;

import game.Game;
import observer.GameObserver;
import observer.ProbabilityDisplayDecorator;
import strategy.SwitchStrategy;
import strategy.StayStrategy;

import java.util.Scanner;

public class GameOfDoors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            Game game = new Game();
            GameObserver observer = new ProbabilityDisplayDecorator(message -> System.out.println(message));
            game.addObserver(observer);

            System.out.println("\nWelcome to the Monty Hall Game!");
            System.out.println("There are 3 doors. Behind one door is a prize, and behind the others, nothing.");

            System.out.print("Choose a door (1, 2, or 3): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next();
                continue;
            }
            int userChoice = scanner.nextInt() - 1;


            int hostOpenedDoor = game.chooseDoor(userChoice);
            System.out.println("Host opened door " + (hostOpenedDoor + 1) + ", which is empty.");
            System.out.println("Probability Hint: Your current door has a 1/3 chance, and the other unopened door has a 2/3 chance.");

            System.out.print("Do you want to switch your choice? (yes/no): ");
            String switchChoice = scanner.next().toLowerCase();

            if (switchChoice.equals("yes")) {
                game.setStrategy(new SwitchStrategy());
            } else if (switchChoice.equals("no")) {
                game.setStrategy(new StayStrategy());
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                continue;
            }

            game.finalizeChoice();

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainChoice = scanner.next().toLowerCase();
            while (!playAgainChoice.equals("yes") & !playAgainChoice.equals("no")){
                System.out.print("Do you want to play again? (yes/no): ");
                playAgainChoice = scanner.next().toLowerCase();
            }

            if (!playAgainChoice.equals("yes")) {
                playAgain = false;
            }

        }

        System.out.println("Thanks for playing! Goodbye!");
        scanner.close();
    }
}