# Monty Hall Game Application

This application implements the classic Monty Hall problem using JavaFX for the UI and several design patterns such as Abstract Factory, Observer, Strategy, and State.

## Features
- **Interactive UI:** Users can select a door, view the host reveal a goat door, and then choose to stay with their original door or switch.
- **Sequential Reveal:** After the final decision, the player’s door is revealed first, then the remaining doors are shown.
- **Visual Feedback:** Uses images (door, goat, car) and border highlights (blue, gray, green, red) to indicate game progress and results.
- **Design Patterns:** 
  - **Abstract Factory:** For door creation.
  - **Observer:** For UI message updates.
  - **Strategy:** To handle the "stay" or "switch" decision.
  - **State:** To manage the different stages of the game.

## How to Run
1. Ensure your project includes the required images in the `/images/` folder:
   - `door.png`
   - `goat.png`
   - `car.png`
2. Build and run the application using your preferred IDE or command-line tools.
3. Follow the on-screen instructions to play the game.

## Demo Video
A demo video is included to showcase the game’s features and user experience.

