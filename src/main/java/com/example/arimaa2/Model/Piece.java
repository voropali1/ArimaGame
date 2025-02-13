package com.example.arimaa2.Model;

import com.example.arimaa2.PieceType;
import com.example.arimaa2.PieceColor;

/**
 * Represents a piece in the game.
 */
public class Piece {
    private PieceType type;
    private PieceColor color;

    /**
     * Creates a new piece with the specified type and color.
     *
     * @param type  the type of the piece
     * @param color the color of the piece
     */
    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Gets the type of the piece.
     *
     * @return the type of the piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Gets the color of the piece.
     *
     * @return the color of the piece
     */
    public PieceColor getColor() {
        return color;
    }
}

