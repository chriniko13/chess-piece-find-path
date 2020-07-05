package com.chriniko.chess;

import com.chriniko.chess.infra.board.Board;
import com.chriniko.chess.infra.piece.PieceColor;
import com.chriniko.chess.infra.piece.Knight;
import com.chriniko.chess.infra.path.Path;
import com.chriniko.chess.infra.board.Position;

import java.util.Set;

/*
	This is a quick and short demonstration of the path calculation.
 */
public class QuickDemonstration {

	public static void main(String[] args) {

		// setup board and knight piece
		Board board = new Board();
		Knight knight = new Knight(PieceColor.WHITE, "knight-white-1");

		Position currentPosition = new Position("D4");
		board.setPiece(knight, currentPosition);

		// create path calculator and provide target position, proceed with calculation
		Position targetPosition = new Position("F8");
		Set<Path> paths = board.calculatePathDFS(currentPosition, targetPosition);

		// print the results
		paths.forEach(pR -> {
			System.out.println(pR.getPathStringified());
			System.out.println();
		});

		System.out.println();

		// create path calculator and provide target position, proceed with calculation
		targetPosition = new Position("D8");
		paths = board.calculatePathDFS(currentPosition, targetPosition);

		// print the results
		paths.forEach(pR -> {
			System.out.println(pR.getPathStringified());
			System.out.println();
		});

		System.out.println();

		// create path calculator and provide target position, proceed with calculation
		targetPosition = new Position("F8");
		paths = board.calculatePathBFS(currentPosition, targetPosition);

		// print the results
		paths.forEach(pR -> {
			System.out.println(pR.getPathStringified());
			System.out.println();
		});

		System.out.println();

		// create path calculator and provide target position, proceed with calculation
		targetPosition = new Position("D8");
		paths = board.calculatePathBFS(currentPosition, targetPosition);

		// print the results
		paths.forEach(pR -> {
			System.out.println(pR.getPathStringified());
			System.out.println();
		});

	}

}
