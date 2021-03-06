/**
 * LightsOut 
 * By: Evan Pratten
 * 
 * This game contains highly reusable code that can be translated over to other grid-based games.
 * There is also tidy logging of the states of the game
 */
package LightsOut;

import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Point;

public class App {
    static int size = 5;

    static GraphicsConsole gc = new GraphicsConsole(600, 600, "Lights Out");

    static Grid gameBoard;

    public static void main(String[] args) {
        System.out.println("[APP] Starting game");

        new App();
    }

    App() {
        // Setup
        gameBoard = new Grid(size, gc.getDrawWidth(), gc.getDrawHeight());

        gc.setAntiAlias(true);
        gc.setLocationRelativeTo(null);
        gc.setBackgroundColor(Color.black);
        gc.clear();
        gc.enableMouse();

        System.out.println("[Game] Setup complete. Starting game loop");

        int tries = 0;

        // loop
        while (!gameBoard.isFull()) {
            // When mouse clicked, deal with state changes
            if (gc.getMouseClick() >= 1) {
                int x = gc.getMouseX();
                int y = gc.getMouseY();

                Point loc = new Point(x, y);
                gameBoard.addressPoints(loc);

                tries++;
            }

            // Display the next frame
            gameBoard.display(gc);
            gc.sleep(5);
        }

        // Winner!
        gc.clear();
        gc.print("Success in " + tries + " tries!");
        
        System.out.println("[Game] The game has been won in "+ tries +" tries");

    }
}
