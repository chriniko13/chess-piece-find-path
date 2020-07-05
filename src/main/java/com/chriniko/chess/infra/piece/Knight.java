package com.chriniko.chess.infra.piece;

import com.chriniko.chess.infra.board.Position;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece {

	public Knight(PieceColor pieceColor, String description) {
		super(pieceColor, description);
	}

	public @Override Set<Position> eligibleMoves(Position currentPosition) {
		int currX = currentPosition.getX();
		int currY = currentPosition.getY();

		// on x(row) can move two up or two down so...
		int upX = currX - 2;
		int downX = currX + 2;

		// ...after every up and down we have two outcomes, move left or move right, so 2 * 2 = 4 possible moves
		int left = currY - 1;
		int right = currY + 1;

		Set<Optional<Position>> result = new HashSet<>(Arrays.asList(
				Position.create(upX, left),
				Position.create(upX, right),
				Position.create(downX, left),
				Position.create(downX, right)
		));

		// same logic as x(row) movement...
		int leftY = currY - 2;
		int rightY = currY + 2;

		int up = currX - 1;
		int down = currX + 1;

		result.addAll(Arrays.asList(
				Position.create(up, leftY),
				Position.create(down, leftY),
				Position.create(up, rightY),
				Position.create(down, rightY)
		));

		return result.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}
}
