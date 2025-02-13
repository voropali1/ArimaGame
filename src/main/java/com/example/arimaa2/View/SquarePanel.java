package com.example.arimaa2.View;

import com.example.arimaa2.GameStatus;
import com.example.arimaa2.GameTimer;
import com.example.arimaa2.Model.Board;
import com.example.arimaa2.PieceColor;
import com.example.arimaa2.PieceType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.io.IOException;
import java.util.logging.*;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import com.example.arimaa2.Model.Piece;

/**
 * Represents a square panel that contains a grid of cells for the game board.
 * Provides methods to initialize and interact with the panel.
 */
public class SquarePanel {
    private final static Logger log = Logger.getLogger(SquarePanel.class.getName());
    public Button startButton = new Button("START");
    public String[] gpieces = {"rabbitg", "rabbitg", "rabbitg", "rabbitg", "rabbitg", "rabbitg", "rabbitg", "rabbitg", "catg", "catg", "dogg", "dogg", "horseg", "horseg", "elephantg", "camelg"};
    public String[] spieces = {"rabbits", "rabbits", "rabbits", "rabbits", "rabbits", "rabbits", "rabbits", "rabbits", "cats", "cats", "dogs", "dogs", "horses", "horses", "elephants", "camels"};
    public GridPane gridPane = new GridPane();
    TextArea textArea = new TextArea();
    String player;
    ImageView imageViewToRemove;
    int rabbitcount = 0;
    int catcount = 0;
    int dogcount = 0;
    int horsecount = 0;
    int elephantcount = 0;
    int camelcount = 0;
    Board modelboard = new Board();
    Label timelabel1;
    int x1 = 9;
    int y1 = 9;
    int x2 = -1;
    int y2 = -1;
    List<String> sshuffledPieces;
    List<String> gshuffledPieces;
    String str;
    int x1for;
    int y1for;
    int x2for;
    int y2for;
    int xforpull;
    int yforpull;
    int xgoldpast = x1;
    int ygoldpast = y1;
    int xsilverpast = x1;
    int ysilverpast = y1;
    GameTimer gameTimer = new GameTimer();

    /**
     * Constructs a SquarePanel object.
     *
     * @param loggingEnabled Flag indicating whether logging is enabled.
     */
    public void SquarePanel(boolean loggingEnabled) {
        if (loggingEnabled) {
            try (InputStream configInputStream = getClass().getClassLoader().getResourceAsStream("logging.properties");) {
                LogManager.getLogManager().readConfiguration(configInputStream);
                log.setLevel(Level.ALL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Logging is enabled");
        } else {
            System.out.println("Logging is disabled");
            log.setLevel(Level.OFF);
        }
        log.info("creating a screen");
        BorderPane root = new BorderPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setStyle("-fx-background-color: #8FBC8F;");
        root.setCenter(gridPane);
        gshuffledPieces = Arrays.asList(gpieces);
        Collections.shuffle(gshuffledPieces);
        sshuffledPieces = Arrays.asList(spieces);
        Collections.shuffle(sshuffledPieces);
        // Creating a BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #8FBC8F;");
        borderPane.setLeft(getGridPane());
        // Creating a scene and adding a BorderPane to it
        Scene scene = new Scene(borderPane, 900, 800);
        // Fill the grid with black and white cells in a checkerboard pattern (traps are yellow)
        log.info("creating a board");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // создание прямоугольника
                Rectangle rect = new Rectangle(74, 74);
                if ((row + col) % 2 == 0) {
                    rect.setFill(Color.BLACK);
                } else {
                    rect.setFill(Color.WHITE);
                }
                if (row == 2 && col == 2 || row == 5 && col == 5 || row == 2 && col == 5 || row == 5 && col == 2) {
                    rect.setFill(Color.YELLOW);
                }
                getGridPane().add(rect, col, row);
            }
        }
        createSilverPieces();
        createGoldPieces();
        if (modelboard.getStatus() == GameStatus.START) {
            getGridPane().setOnMouseClicked(event -> {
                changeFigure(event);
            });
        }
    }

    /**
     * Creates silver pieces on the game board.
     * The pieces are randomly placed on the board based on the shuffled silver piece list.
     * Updates the model board with the created silver pieces.
     */
    public void createSilverPieces() {
        int col = 0;
        int row = 0;
        for (int i = 0; i <= gpieces.length - 1; ) {
            // the figures are randomly placed
            Image gpieceImage = new Image(getClass().getResource("/" + sshuffledPieces.get(i) + ".png").toExternalForm());
            ImageView gpieceImageView = new ImageView(gpieceImage);
            gpieceImageView.setFitWidth(74);
            gpieceImageView.setFitHeight(74);
            getGridPane().add(gpieceImageView, col, row);
            //board[col][row] = gpieceImageView;
            if (sshuffledPieces.get(i).equals("rabbits")) {
                modelboard.createPiece(col, row, PieceType.RABBIT, PieceColor.SILVER);
                rabbitcount++;
            }
            if (sshuffledPieces.get(i).equals("cats")) {
                modelboard.createPiece(col, row, PieceType.CAT, PieceColor.SILVER);
                catcount++;
            }
            if (sshuffledPieces.get(i) == "dogs") {
                modelboard.createPiece(col, row, PieceType.DOG, PieceColor.SILVER);
                dogcount++;
            }
            if (sshuffledPieces.get(i) == "horses") {
                modelboard.createPiece(col, row, PieceType.HORSE, PieceColor.SILVER);
                horsecount++;
            }
            if (sshuffledPieces.get(i) == "elephants") {
                modelboard.createPiece(col, row, PieceType.ELEPHANT, PieceColor.SILVER);
                elephantcount++;
            }
            if (sshuffledPieces.get(i) == "camels") {
                modelboard.createPiece(col, row, PieceType.CAMEL, PieceColor.SILVER);
                camelcount++;
            }
            i++;
            col++;
            if (col == 8) {
                col = 0;
                row++;
            }
        }
        log.info("creating silver figurines");
    }

    /**
     * Creates gold pieces on the game board.
     * The pieces are randomly placed on the board based on the shuffled gold piece list.
     * Updates the model board with the created gold pieces.
     */
    public void createGoldPieces() {
        int cols = 0;
        int rows = 6;
        for (int i = 0; i <= spieces.length - 1; ) {
            // the figures are randomly placed
            Image spieceImage = new Image((getClass().getResource("/" + gshuffledPieces.get(i) + ".png").toExternalForm()));
            ImageView spieceImageView = new ImageView(spieceImage);
            spieceImageView.setFitWidth(74);
            spieceImageView.setFitHeight(74);
            getGridPane().add(spieceImageView, cols, rows);
            if (gshuffledPieces.get(i) == "rabbitg") {
                modelboard.createPiece(cols, rows, PieceType.RABBIT, PieceColor.GOLD);
                rabbitcount++;
            }
            if (gshuffledPieces.get(i) == "catg") {
                modelboard.createPiece(cols, rows, PieceType.CAT, PieceColor.GOLD);
                catcount++;
            }
            if (gshuffledPieces.get(i) == "dogg") {
                modelboard.createPiece(cols, rows, PieceType.DOG, PieceColor.GOLD);
                dogcount++;
            }
            if (gshuffledPieces.get(i) == "horseg") {
                modelboard.createPiece(cols, rows, PieceType.HORSE, PieceColor.GOLD);
                horsecount++;
            }
            if (gshuffledPieces.get(i) == "elephantg") {
                modelboard.createPiece(cols, rows, PieceType.ELEPHANT, PieceColor.GOLD);
                elephantcount++;
            }
            if (gshuffledPieces.get(i) == "camelg") {
                modelboard.createPiece(cols, rows, PieceType.CAMEL, PieceColor.GOLD);
                camelcount++;
            }
            i++;
            cols++;
            if (cols == 8) {
                cols = 0;
                rows++;
            }
        }
        log.info("creating gold figurines");
    }

    /**
     * Handles the change of a figure on the game board based on the mouse event.
     * Determines the action to be taken based on the game status and the selected figure.
     *
     * @param event The mouse event that triggers the figure change.
     */
    public void changeFigure(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        x2 = GridPane.getColumnIndex(clickedNode);
        y2 = GridPane.getRowIndex(clickedNode);
        if (modelboard.getStatus() == GameStatus.START) {
            //the figure changes in increasing strength
            change(clickedNode);
        } else if (modelboard.getStatus() == GameStatus.PROCESS) {
            if (modelboard.getSelectedPiece(x2, y2) != null && modelboard.getRemainingMoves() && modelboard.getPlayerColor().equals(modelboard.getSelectedPiece(x2, y2).getColor())) {
                normalSelect(clickedNode);
            } else if (modelboard.getSelectedPiece(x2, y2) != null && (!modelboard.getPlayerColor().equals(modelboard.getSelectedPiece(x2, y2).getColor())) && modelboard.getRemainingMoves() && modelboard.getHaveMoves()) {
                //the player wants to make a move using a figure of a different color
                differentColors(clickedNode);
            } else if (modelboard.getSelectedPiece(x2, y2) != null && (!modelboard.getPlayerColor().equals(modelboard.getSelectedPiece(x2, y2).getColor())) && modelboard.getRemainingMoves()) {
                //the player wants to make a move using a figure of a different color while not yet having moves
                differentColorsAndDontHavesMoves(clickedNode);
            } else {
                //the figures are moving
                if (modelboard.canMoveXY(x2, y2, x1, y1) && modelboard.getSelectedPiece(x1, y1).getColor().equals(modelboard.getPlayerColor())) {
                    List<Node> children = gridPane.getChildren();
                    imageViewToRemove = null;
                    //we are looking for a picture of a figure that will need to be removed later
                    searchImageViewToRemove(children);
                    if (imageViewToRemove != null && modelboard.isEmpty(x2, y2) && modelboard.getRemainingMoves()) {
                        if (modelboard.isTrap(x2, y2)) {
                            //the figure fell into a trap
                            trap();
                        } else if (modelboard.isEmpty(x2, y2)) {
                            if (modelboard.isEmpty(x2, y2) && modelboard.getSelectedPiece(x1, y1) != null && modelboard.getCanMoveType(modelboard.getSelectedPiece(x1, y1).getType()) && modelboard.getRemainingMoves() && modelboard.getSelectedPiece(x1, y1).getColor().equals(modelboard.getPlayerColor())) {
                                normalMove();
                            } else if (modelboard.isEmpty(x2, y2) && modelboard.getSelectedPiece(x1, y1) != null && modelboard.getSelectedPiece(x1, y1).getColor().equals(PieceColor.SILVER) && modelboard.getRemainingMoves() && modelboard.getSelectedPiece(x1, y1).getColor().equals(modelboard.getPlayerColor())) {
                                //the chosen figure is a rabbit(one of the colors), special rules (it cannot make a move backwards)
                                rabbitsMove1();
                            } else if (modelboard.isEmpty(x2, y2) && modelboard.getSelectedPiece(x1, y1) != null && modelboard.getRemainingMoves() && modelboard.getSelectedPiece(x1, y1).getColor().equals(modelboard.getPlayerColor())) {
                                //the chosen figure is a rabbit(one of the colors), special rules (it cannot make a move backwards)
                                rabbitsMove2();
                            }
                        }
                    }
                } else if (modelboard.canMoveXY(x2, y2, x1, y1)) {
                    List<Node> children = gridPane.getChildren();
                    imageViewToRemove = null;
                    //we are looking for a picture of a figure that will need to be removed later
                    searchImageViewToRemove(children);
                    if (modelboard.getSelectedPiece(x1, y1) != null && modelboard.canPull(x2for, y2for, xforpull, yforpull) && modelboard.getRemainingMoves() && x2 == x1for && y2 == y1for && modelboard.getPower(x2for, y2for, xforpull, yforpull)) {  //tazeni
                        //the figure is trying to pull on another figure
                        pull();
                    }
                    if (modelboard.getSelectedPiece(x1, y1) != null && modelboard.getRemainingMoves()) {
                        //a figure tries to push another figurine
                        push(children);
                    }
                }
            }
        }
    }

    /**
     * Handles the win condition of the game.
     * Determines the winning player based on the game board's winner.
     */
    public void win() {
        //determining the winning player
        if (modelboard.getWinner() == "GOLD") {
            player = "GOLD";
        } else if (modelboard.getWinner() == "SILVER") {
            player = "SILVER";
        }
    }

    /**
     * Changes the piece on the game board to the specified piece type and color.
     *
     * @param clickedNode The clicked node representing the piece to be changed.
     * @param gcolI       The column index of the piece.
     * @param growI       The row index of the piece.
     * @param pieceType   The new piece type.
     * @param pieceColor  The new piece color.
     */
    private void changePiece(Node clickedNode, int gcolI, int growI, PieceType pieceType, PieceColor pieceColor) {
        Image nextPieceImage = new Image(getClass().getResource("/" + modelboard.getNextPieceName(gcolI, growI, pieceType, pieceColor) + ".png").toExternalForm());
        ImageView nextPieceImageView = new ImageView(nextPieceImage);
        nextPieceImageView.setFitWidth(74);
        nextPieceImageView.setFitHeight(74);
        getGridPane().getChildren().remove(clickedNode);
        getGridPane().add(nextPieceImageView, gcolI, growI);
        log.info("The player changed the figure");
    }

    /**
     * Returns the grid pane of the game board.
     *
     * @return The grid pane of the game board.
     */
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Returns the start button of the game.
     *
     * @return The start button of the game.
     */
    public Button getStartButton() {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event3) {
                //players click on start and the game begins
                if (modelboard.getCount(rabbitcount, catcount, dogcount, horsecount, elephantcount, camelcount) && modelboard.getStatus() == GameStatus.START) {
                    modelboard.changeGameStatus(GameStatus.PROCESS);
                    startButton.setText("NEXT PLAYER");
                    gameTimer.timerstart(getLabel());
                    textArea.setText("THE GOLD PLAYER MAKES A MOVE");
                    getTextAreaInView().setStyle("-fx-text-fill: black;");
                    modelboard.getNextPlayer("THE GOLD PLAYER MAKES A MOVE");
                    setTextAreainModel();
                    modelboard.setTextArea("THE GOLD PLAYER MAKES A MOVE");
                    log.info("The game has started");
                } else if (modelboard.getStatus() == GameStatus.START) {
                    getTextAreaInView().setStyle("-fx-text-fill: red;");
                    getTextAreaInView().setText("WRONG NUMBERS OF FIGURES!");
                } else if (modelboard.getStatus() == GameStatus.PROCESS && modelboard.getHaveMoves()) {
                    //switching to another player
                    startButton.setText("NEXT PLAYER");
                    if (modelboard.getPlayerColor() == PieceColor.GOLD && modelboard.getRemainingMoves()) {
                        textArea.setText("THE SILVER PLAYER MAKES A MOVE");
                        modelboard.setPlayerColor(PieceColor.SILVER);
                        modelboard.setTextArea("THE SILVER PLAYER MAKES A MOVE");
                        modelboard.getNextPlayer("THE SILVER PLAYER MAKES A MOVE");
                        gameTimer.timerstart(getLabel());
                        log.info("Player Change");
                    } else if (modelboard.getRemainingMoves()) {
                        textArea.setText("THE GOLD PLAYER MAKES A MOVE");
                        modelboard.setPlayerColor(PieceColor.GOLD);
                        modelboard.setTextArea("THE GOLD PLAYER MAKES A MOVE");
                        modelboard.getNextPlayer("THE GOLD PLAYER MAKES A MOVE");
                        gameTimer.timerstart(getLabel());
                        log.info("Player Change");
                    }
                    setTextAreainModel();
                }
            }
        });
        return startButton;
    }

    /**
     * Handles the end condition of the game.
     * Stops the game timer, updates the text area with the winning player,
     * changes the game status to END, and resets the player variable.
     */
    public void end() {
        if (player == "SILVER") {
            gameTimer.stop();
            textArea.setText("THE SILVER PLAYER WON");
            modelboard.changeGameStatus(GameStatus.END);
            player = null;
            log.info("The silver player won");
        } else if (player == "GOLD") {
            gameTimer.stop();
            textArea.setText("THE GOLD PLAYER WON");
            modelboard.changeGameStatus(GameStatus.END);
            player = null;
            log.info("The gold player won");
        }
    }

    /**
     * Returns the text content of the text area.
     *
     * @return The text content of the text area.
     */
    public String getTextArea() {
        textArea.setEditable(false);
        return str;
    }

    /**
     * Returns the text area used for displaying game messages in the view.
     *
     * @return The text area used for displaying game messages.
     */
    public TextArea getTextAreaInView() {
        return textArea;
    }

    /**
     * Handles the click event on a game piece.
     *
     * @param piece The clicked game piece.
     */
    public void handlePieceClick(Piece piece) {
        modelboard.selectPiece(piece);
    }

    /**
     * Handles the click event on a game cell.
     *
     * @param x2 The column index of the clicked cell.
     * @param y2 The row index of the clicked cell.
     * @param x1 The previous column index of the selected piece.
     * @param y1 The previous row index of the selected piece.
     */
    public void handleCellClick(int x2, int y2, int x1, int y1) {
        modelboard.movePieceToCell(x2, y2, x1, y1);
    }

    /**
     * Sets the text area in the model to "THE GOLD PLAYER MAKES A MOVE" and updates the local string variable.
     */
    public void setTextAreainModel() {
        modelboard.setTextArea("THE GOLD PLAYER MAKES A MOVE");
        str = "THE GOLD PLAYER MAKES A MOVE";
    }

    /**
     * Returns the label used for displaying the game timer.
     *
     * @return The label used for displaying the game timer.
     */
    public Label getLabel() {
        return timelabel1;
    }

    /**
     * Sets the label used for displaying the game timer.
     *
     * @param timelabel The label used for displaying the game timer.
     */
    public void setLabel(Label timelabel) {
        timelabel1 = timelabel;
    }

    /**
     * Determines the winning player and updates the game status accordingly.
     * If the player is SILVER, sets the text area to "THE SILVER PLAYER WON"
     * and changes the game status to END.
     * If the player is GOLD, sets the text area to "THE GOLD PLAYER WON"
     * and changes the game status to END.
     */
    public void controlWin() {
        if (player == "SILVER") {
            textArea.setText("THE SILVER PLAYER WON");
            modelboard.changeGameStatus(GameStatus.END);
            end();
        } else if (player == "GOLD") {
            textArea.setText("THE GOLD PLAYER WON");
            modelboard.changeGameStatus(GameStatus.END);
            end();
        }
    }

    /**
     * Restarts the game timer if there are no remaining moves for the timer.
     * Updates the text area and player color based on the current player.
     * If the player color is GOLD, sets the text area to "THE GOLD PLAYER MAKES A MOVE".
     * If the player color is SILVER, sets the text area to "THE SILVER PLAYER MAKES A MOVE".
     * Logs the restart of the timer.
     */
    public void restartTimer() {
        if (!(modelboard.getRemainingMovesForTimer())) {
            gameTimer.timerstart(getLabel());
            if (modelboard.getPlayerColor().equals(PieceColor.GOLD)) {
                modelboard.setTextArea("THE GOLD PLAYER MAKES A MOVE");
                modelboard.getNextPlayer("THE GOLD PLAYER MAKES A MOVE");
                textArea.setText("THE GOLD PLAYER MAKES A MOVE");
            } else {
                modelboard.setTextArea("THE SILVER PLAYER MAKES A MOVE");
                modelboard.getNextPlayer("THE SILVER PLAYER MAKES A MOVE");
                textArea.setText("THE SILVER PLAYER MAKES A MOVE");
            }
            log.info("Restart the timer");
        }
    }

    /**
     * Changes the location of a game piece on the board based on the clicked node.
     * Updates the piece counts and the model board accordingly.
     *
     * @param clickedNode The clicked node representing the game piece.
     */
    public void change(Node clickedNode) {
        if (clickedNode != getGridPane() && (!clickedNode.getClass().equals(Rectangle.class))) {
            Image image = ((ImageView) clickedNode).getImage();
            String url = image.getUrl();
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String pieceName = fileName.substring(0, fileName.lastIndexOf("."));
            if (clickedNode.getClass().equals(ImageView.class)) {
                switch (pieceName) {
                    case "rabbitg":
                        changePiece(clickedNode, x2, y2, PieceType.CAT, PieceColor.GOLD);
                        rabbitcount--;
                        catcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.CAT, PieceColor.GOLD);
                        break;
                    case "catg":
                        changePiece(clickedNode, x2, y2, PieceType.DOG, PieceColor.GOLD);
                        catcount--;
                        dogcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.DOG, PieceColor.GOLD);
                        break;
                    case "dogg":
                        changePiece(clickedNode, x2, y2, PieceType.HORSE, PieceColor.GOLD);
                        dogcount--;
                        horsecount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.HORSE, PieceColor.GOLD);
                        break;
                    case "horseg":
                        changePiece(clickedNode, x2, y2, PieceType.ELEPHANT, PieceColor.GOLD);
                        horsecount--;
                        elephantcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.ELEPHANT, PieceColor.GOLD);
                        break;
                    case "elephantg":
                        changePiece(clickedNode, x2, y2, PieceType.CAMEL, PieceColor.GOLD);
                        elephantcount--;
                        camelcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.CAMEL, PieceColor.GOLD);
                        break;
                    case "camelg":
                        changePiece(clickedNode, x2, y2, PieceType.RABBIT, PieceColor.GOLD);
                        camelcount--;
                        rabbitcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.RABBIT, PieceColor.GOLD);
                        break;
                    case "rabbits":
                        changePiece(clickedNode, x2, y2, PieceType.CAT, PieceColor.SILVER);
                        rabbitcount--;
                        catcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.CAT, PieceColor.SILVER);
                        break;
                    case "cats":
                        changePiece(clickedNode, x2, y2, PieceType.DOG, PieceColor.SILVER);
                        catcount--;
                        dogcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.DOG, PieceColor.SILVER);
                        break;
                    case "dogs":
                        changePiece(clickedNode, x2, y2, PieceType.HORSE, PieceColor.SILVER);
                        dogcount--;
                        horsecount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.HORSE, PieceColor.SILVER);
                        break;
                    case "horses":
                        changePiece(clickedNode, x2, y2, PieceType.ELEPHANT, PieceColor.SILVER);
                        horsecount--;
                        elephantcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.ELEPHANT, PieceColor.SILVER);
                        break;
                    case "elephants":
                        changePiece(clickedNode, x2, y2, PieceType.CAMEL, PieceColor.SILVER);
                        elephantcount--;
                        camelcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.CAMEL, PieceColor.SILVER);
                        break;
                    case "camels":
                        changePiece(clickedNode, x2, y2, PieceType.RABBIT, PieceColor.SILVER);
                        camelcount--;
                        rabbitcount++;
                        modelboard.updateForChange(x2, y2);
                        modelboard.createPiece(x2, y2, PieceType.RABBIT, PieceColor.SILVER);
                        break;
                }
                log.info("The player changed the location of the figures");
            }
        }
    }

    /**
     * Determines the coordinates of the last selected figure.
     * If the selected piece color is GOLD, sets the xgoldpast and ygoldpast variables to the current coordinates.
     * If the selected piece color is SILVER, sets the xsilverpast and ysilverpast variables to the current coordinates.
     */

    public void pastCoordinate() {
        //determining the coordinates of the last selected figure
        if (modelboard.getSelectedPiece(x2, y2).getColor().equals(PieceColor.GOLD)) {
            xgoldpast = x2;
            ygoldpast = y2;
        } else {
            xsilverpast = x2;
            ysilverpast = y2;
        }
    }

    /**
     * Handles the selection of a game piece when the selected piece and clicked node have the same color.
     * Updates the x2for, y2for, x1, and y1 variables based on the selected piece and clicked node.
     * Calls the pastCoordinate() method to determine the coordinates of the last selected figure.
     * Logs the selection of a figure by the player.
     *
     * @param clickedNode The clicked node representing the game piece.
     */
    public void normalSelect(Node clickedNode) {
        handlePieceClick(modelboard.getSelectedPiece(x2, y2));
        x2for = x2;
        y2for = y2;
        y1 = GridPane.getRowIndex(clickedNode);
        x1 = GridPane.getColumnIndex(clickedNode);
        pastCoordinate();
        log.info("The player chose a figure");
    }

    /**
     * Handles the selection of a game piece when the selected piece and clicked node have different colors.
     * Updates the x2for, y2for, x1, and y1 variables based on the selected piece and clicked node.
     * Calls the pastCoordinate() method to determine the coordinates of the last selected figure.
     * Sets the player color in the model board based on the current player.
     * Logs the selection of a figure of a different color by the player.
     *
     * @param clickedNode The clicked node representing the game piece.
     */
    public void differentColors(Node clickedNode) {
        if (modelboard.getPlayerColor() == PieceColor.SILVER || modelboard.getNextPlayer(getTextArea()).equals(PieceColor.SILVER)) {
            handlePieceClick(modelboard.getSelectedPiece(x2, y2));
            x2for = x2;
            y2for = y2;
            y1 = GridPane.getRowIndex(clickedNode);
            x1 = GridPane.getColumnIndex(clickedNode);
            pastCoordinate();
            modelboard.setPlayerColor(PieceColor.SILVER);
        } else {
            handlePieceClick(modelboard.getSelectedPiece(x2, y2));
            x2for = x2;
            y2for = y2;
            y1 = GridPane.getRowIndex(clickedNode);
            x1 = GridPane.getColumnIndex(clickedNode);
            pastCoordinate();
            modelboard.setPlayerColor(PieceColor.GOLD);
        }
        log.info("The player chose a figure of a different color");
    }

    /**
     * Handles the selection of a game piece when the selected piece and clicked node have different colors,
     * and the selected piece has no moves yet.
     * Updates the x2for, y2for, x1, and y1 variables based on the selected piece and clicked node.
     * Calls the pastCoordinate() method to determine the coordinates of the last selected figure.
     * Sets the player color in the model board based on the current player.
     * Logs the selection of a figure of a different color by the player without having moves.
     *
     * @param clickedNode The clicked node representing the game piece.
     */
    public void differentColorsAndDontHavesMoves(Node clickedNode) {
        if (modelboard.getPlayerColor() == PieceColor.SILVER || modelboard.getNextPlayer(getTextArea()).equals(PieceColor.SILVER)) {
            handlePieceClick(modelboard.getSelectedPiece(x2, y2));
            x2for = x2;
            y2for = y2;
            y1 = GridPane.getRowIndex(clickedNode);
            x1 = GridPane.getColumnIndex(clickedNode);
            pastCoordinate();
            modelboard.setPlayerColor(PieceColor.SILVER);
        } else {
            handlePieceClick(modelboard.getSelectedPiece(x2, y2));
            x2for = x2;
            y2for = y2;
            y1 = GridPane.getRowIndex(clickedNode);
            x1 = GridPane.getColumnIndex(clickedNode);
            modelboard.setPlayerColor(PieceColor.GOLD);
            pastCoordinate();
        }
        log.info("The player chose a figure of a different color without having moves");
    }

    /**
     * Searches for an ImageView node to remove from the grid pane based on the coordinates x1 and y1.
     * If a matching ImageView node is found, sets the imageViewToRemove variable to that node.
     *
     * @param children The list of nodes in the grid pane.
     */
    public void searchImageViewToRemove(List<Node> children) {
        for (Node child : children) {
            int row = GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child);
            if (column == x1 && row == y1 && child instanceof ImageView) {
                imageViewToRemove = (ImageView) child;
                break;
            }
        }
    }

    /**
     * Handles the player getting trapped.
     * Removes the imageViewToRemove node from the grid pane.
     * Calls the handleCellClick method with the coordinates x2, y2, x1, and y1.
     * Restarts the timer.
     * Logs that the player is trapped.
     */
    public void trap() {
        gridPane.getChildren().remove(imageViewToRemove);
        handleCellClick(x2, y2, x1, y1);
        restartTimer();
        log.info("The player is trapped");
    }

    /**
     * Handles a normal move by the player.
     * Removes the imageViewToRemove node from the grid pane.
     * Adds the imageViewToRemove node to the grid pane at coordinates x2, y2.
     * Calls the handleCellClick method with the coordinates x2, y2, x1, and y1.
     * Calls the equating method.
     * Restarts the timer.
     * Logs that the player made a normal move.
     */
    public void normalMove() {
        gridPane.getChildren().remove(imageViewToRemove);
        gridPane.add(imageViewToRemove, x2, y2);
        handleCellClick(x2, y2, x1, y1);
        equating();
        restartTimer();
        log.info("The player made a normal move");
    }

    /**
     * Handles a move with a rabbit by the player.
     * If the y2 coordinate is greater than or equal to y1, performs the following actions:
     * - Removes the imageViewToRemove node from the grid pane.
     * - Adds the imageViewToRemove node to the grid pane at coordinates x2, y2.
     * - Calls the handleCellClick method with the coordinates x2, y2, x1, and y1.
     * - Calls the equating method.
     * - Calls the win method.
     * - Calls the controlWin method.
     * - Restarts the timer.
     * Logs that the player made a move with a rabbit.
     */
    public void rabbitsMove1() {
        if (y2 >= y1) {
            gridPane.getChildren().remove(imageViewToRemove);
            gridPane.add(imageViewToRemove, x2, y2);
            handleCellClick(x2, y2, x1, y1);
            equating();
            win();
            controlWin();
            restartTimer();
            log.info("The player made a move with a rabbit.1");
        }
    }

    /**
     * Handles a move with a rabbit by the player.
     * If the y1 coordinate is greater than or equal to y2, performs the following actions:
     * - Removes the imageViewToRemove node from the grid pane.
     * - Adds the imageViewToRemove node to the grid pane at coordinates x2, y2.
     * - Calls the handleCellClick method with the coordinates x2, y2, x1, and y1.
     * - Calls the equating method.
     * - Calls the win method.
     * - Calls the controlWin method.
     * - Restarts the timer.
     * Logs that the player made a move with a rabbit.
     */
    public void rabbitsMove2() {
        if (y1 >= y2) {
            gridPane.getChildren().remove(imageViewToRemove);
            gridPane.add(imageViewToRemove, x2, y2);
            handleCellClick(x2, y2, x1, y1);
            equating();
            win();
            controlWin();
            restartTimer();
            log.info("The player made a move with a rabbit.2");
        }
    }

    /**
     * Handles the player pulling a figure.
     * Removes the imageViewToRemove node from the grid pane.
     * Adds the imageViewToRemove node to the grid pane at coordinates x2, y2.
     * Calls the handleCellClick method with the coordinates x2, y2, x1, and y1.
     * Calls the equating method.
     * Calls the win method.
     * Calls the controlWin method.
     * Restarts the timer.
     * Logs that the player pulled the figure.
     */
    public void pull() {
        gridPane.getChildren().remove(imageViewToRemove);
        gridPane.add(imageViewToRemove, x2, y2);
        handleCellClick(x2, y2, x1, y1);
        equating();
        win();
        controlWin();
        restartTimer();
        log.info("The player pulled the figure");
    }

    /**
     * Handles the player pushing a figure.
     * Checks if the player's color is GOLD and they can push the figure from x2for, y2for to xgoldpast, ygoldpast
     * using the getPower method, or if the player's color is SILVER and they can push the figure from x2for, y2for
     * to xsilverpast, ysilverpast using the getPower method.
     * If the conditions are met, performs the following actions:
     * - Removes the imageViewToRemove node from the grid pane.
     * - If the target cell (x2, y2) is not a trap, adds the imageViewToRemove node to the grid pane at coordinates x2, y2.
     * - Calls the handleCellClick method with the coordinates x2, y2, x2for, and y2for.
     * - If the player's color is GOLD, calls the pushGold method with the list of nodes as a parameter.
     * - If the player's color is SILVER, calls the pushSilver method with the list of nodes as a parameter.
     * - Calls the equating method.
     * - Calls the win method.
     * - Calls the controlWin method.
     * - Restarts the timer.
     *
     * @param children The list of nodes in the grid pane.
     */
    public void push(List<Node> children) {
        if (modelboard.getPlayerColor().equals(PieceColor.GOLD) && modelboard.canPush(x2for, y2for, xgoldpast, ygoldpast) && modelboard.getPower(x2for, y2for, xgoldpast, ygoldpast) || modelboard.getPlayerColor().equals(PieceColor.SILVER) && modelboard.canPush(x2for, y2for, xsilverpast, ysilverpast) && modelboard.getPower(x2for, y2for, xsilverpast, ysilverpast)) {
            gridPane.getChildren().remove(imageViewToRemove);
            if (!(modelboard.isTrap(x2, y2))) {
                gridPane.add(imageViewToRemove, x2, y2);
            }
            handleCellClick(x2, y2, x2for, y2for);
            if (modelboard.getPlayerColor().equals(PieceColor.GOLD)) {
                pushGold(children);
            } else {
                pushSilver(children);
            }
            equating();
            win();
            controlWin();
            restartTimer();
        }
    }

    /**
     * Handles the player pushing a figure as a GOLD player.
     * Searches for the imageViewToRemove node in the list of nodes based on the xgoldpast and ygoldpast coordinates.
     * Removes the imageViewToRemove node from the grid pane.
     * Adds the imageViewToRemove node to the grid pane at coordinates x2for, y2for.
     * Calls the handlePieceClick method with the piece of the selected figure at xgoldpast, ygoldpast.
     * Calls the handleCellClick method with the coordinates x2for, y2for, xgoldpast, and ygoldpast.
     * Logs that the GOLD player pushed the figure.
     *
     * @param children The list of nodes in the grid pane.
     */
    public void pushGold(List<Node> children) {
        for (Node child : children) {
            int row = GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child);
            if (column == xgoldpast && row == ygoldpast && child instanceof ImageView) {
                imageViewToRemove = (ImageView) child;
                break;
            }
        }
        gridPane.getChildren().remove(imageViewToRemove);
        gridPane.add(imageViewToRemove, x2for, y2for);
        handlePieceClick(modelboard.getSelectedPiece(xgoldpast, ygoldpast));
        handleCellClick(x2for, y2for, xgoldpast, ygoldpast);
        log.info("The playerG pushed the figure");
    }

    /**
     * Handles the player pushing a figure as a SILVER player.
     * Searches for the imageViewToRemove node in the list of nodes based on the xsilverpast and ysilverpast coordinates.
     * Removes the imageViewToRemove node from the grid pane.
     * Adds the imageViewToRemove node to the grid pane at coordinates x2for, y2for.
     * Calls the handlePieceClick method with the piece of the selected figure at xsilverpast, ysilverpast.
     * Calls the handleCellClick method with the coordinates x2for, y2for, xsilverpast, and ysilverpast.
     * Logs that the SILVER player pushed the figure.
     *
     * @param children The list of nodes in the grid pane.
     */
    public void pushSilver(List<Node> children) {
        for (Node child : children) {
            int row = GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child);
            if (column == xsilverpast && row == ysilverpast && child instanceof ImageView) {
                imageViewToRemove = (ImageView) child;
                break;
            }
        }
        gridPane.getChildren().remove(imageViewToRemove);
        gridPane.add(imageViewToRemove, x2for, y2for);
        handlePieceClick(modelboard.getSelectedPiece(xsilverpast, ysilverpast));
        handleCellClick(x2for, y2for, xsilverpast, ysilverpast);
        log.info("The playerS pushed the figure");
    }

    /**
     * Assigns values for push-related variables.
     * Sets x1for to x1.
     * Sets y1for to y1.
     * Sets xforpull to x2.
     * Sets yforpull to y2.
     * Sets y1 to y2.
     * Sets x1 to x2.
     */
    public void equating() {
        //assigning values for pushes
        x1for = x1;
        y1for = y1;
        xforpull = x2;
        yforpull = y2;
        y1 = y2;
        x1 = x2;
    }
}
