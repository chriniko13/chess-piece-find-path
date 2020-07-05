package com.chriniko.chess.infra.piece;

import com.chriniko.chess.infra.board.Position;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Pawn extends Piece {

	public Pawn(PieceColor pieceColor, String description) {
		super(pieceColor, description);
	}

	public @Override Set<Position> eligibleMoves(Position currentPosition) {

		int currX = currentPosition.getX();
		int currY = currentPosition.getY();

		int left = currY - 1;
		int right = currY + 1;

		int up = currX - 1;

		Set<Optional<Position>> result = new HashSet<>(Arrays.asList(
				Position.create(up, left),
				Position.create(up, right)
		));

		return result.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
	}
}
