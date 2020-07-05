package com.chriniko.chess.infra.board;

import com.chriniko.chess.infra.NotThreadSafe;
import com.chriniko.chess.infra.path.BFSPathCalculator;
import com.chriniko.chess.infra.path.DFSPathCalculator;
import com.chriniko.chess.infra.path.Path;
import com.chriniko.chess.infra.path.PathCalculator;
import com.chriniko.chess.infra.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@NotThreadSafe
public class Board {

	private static final int SIZE = 8;

	private final Map<String, PathCalculator> pathCalculators = new HashMap<>();

	private final Piece[][] pieces = new Piece[SIZE][SIZE];

	/**
	 * Maintain also retrieval by position for constant access rather than linear if searching on {@link Board#pieces}
	 */
	private final Map<Position, Piece> piecesByPosition = new HashMap<>();

	private int piecesOnBoard = 0;

	public Board() {
		pathCalculators.put("dfs", new DFSPathCalculator());
		pathCalculators.put("bfs", new BFSPathCalculator());
	}

	public void setPiece(Piece piece, Position position) {
		pieces[position.getX()][position.getY()] = piece;
		piecesByPosition.put(position, piece);
		piecesOnBoard++;
	}

	public void removePiece(Position position) {
		pieces[position.getX()][position.getY()] = null;
		piecesByPosition.remove(position);
		piecesOnBoard--;
	}

	public Piece getPiece(Position position) {
		return piecesByPosition.get(position);
	}

	public boolean isEmpty() {
		return piecesOnBoard == 0;
	}

	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public Set<Path> calculatePathDFS(Position start, Position end) {
		return calculatePath(start, end, "dfs");
	}

	public Set<Path> calculatePathBFS(Position start, Position end) {
		return calculatePath(start, end, "bfs");
	}

	private Set<Path> calculatePath(Position start, Position end, String approach) {
		Piece piece = piecesByPosition.get(start);
		if (piece == null) {
			throw new IllegalStateException("provided start position does not have a chess piece!");
		}
		return pathCalculators.get(approach).process(piece, start, end);
	}
}
