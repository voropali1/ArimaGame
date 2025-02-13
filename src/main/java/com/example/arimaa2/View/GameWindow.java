package com.example.arimaa2.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

/**
 * Represents the game window.
 */
public class GameWindow {
    private SquarePanel squarePanel1 = new SquarePanel();
    private Label timeLabel;

    /**
     * Starts the game and initializes the game window.
     *
     * @param stage   the JavaFX stage for the game window
     * @param loggery specifies whether logging is enabled or disabled
     */
    public void startGame(Stage stage, boolean loggery) {
        BorderPane borderPane = new BorderPane();
        BorderPane topButtonContainer = new BorderPane();
        topButtonContainer.setTop(squarePanel1.getStartButton());
        timeLabel = new Label();
        Font font = new Font(26);
        timeLabel.setFont(font);
        timeLabel.setStyle("-fx-font-size: 10px;");
        timeLabel.setStyle("-fx-background-color: #8FBC8F;");
        timeLabel.setAlignment(Pos.CENTER_RIGHT);
        squarePanel1.setLabel(timeLabel);
        topButtonContainer.setCenter(timeLabel);
        borderPane.setRight(topButtonContainer);
        BorderPane.setMargin(topButtonContainer, new Insets(20, 115, 100, 0));
        borderPane.setStyle("-fx-background-color: #8FBC8F;");
        borderPane.setBottom(squarePanel1.getTextAreaInView());
        squarePanel1.SquarePanel(loggery);
        borderPane.setLeft(squarePanel1.getGridPane());
        Scene scene = new Scene(borderPane, 900, 800);
        stage.setTitle("Arimaa Game");
        stage.setScene(scene);
        stage.show();
    }
}
