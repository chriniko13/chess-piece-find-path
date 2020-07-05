package com.chriniko.chess.infra.piece;

import com.chriniko.chess.infra.board.Board;
import com.chriniko.chess.infra.board.Position;

import java.util.Set;

/**
 * Represents a chess piece, such as knight, queen, bishop, etc on top of a {@link Board}
 */
public abstract class Piece {

	protected final PieceColor pieceColor;
	protected final String description;

	protected Piece(PieceColor pieceColor, String description) {
		this.pieceColor = pieceColor;
		this.description = description;
	}

	public abstract Set<Position> eligibleMoves(Position currentPosition);

	public PieceColor getPieceColor() {
		return pieceColor;
	}

	public String getDescription() {
		return description;
	}
}
