package com.example.arimaa2;

import com.example.arimaa2.View.GameWindow;
import com.example.arimaa2.View.SquarePanel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class that launches the Arimaa game application.
 */
public class Main extends Application {
    private GameWindow gameWindow = new GameWindow();
    private SquarePanel squarePanel = new SquarePanel();
    private boolean loggingEnabled = true;

    /**
     * The main entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the Arimaa game application.
     *
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        gameWindow.startGame(stage, loggingEnabled);
    }
}



