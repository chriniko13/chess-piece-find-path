package com.chriniko.chess.infra.piece;

import com.chriniko.chess.infra.board.Position;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PawnTest {

	@Test
	public void eligibleMovesWorksAsExpected() {

		// given
		Pawn pawn = new Pawn(PieceColor.BLACK, "pawn1-black");

		// when
		Set<Position> result = pawn.eligibleMoves(new Position("C3"));

		// then
		Set<Position> expected = new HashSet<>();
		expected.add(new Position("B4"));
		expected.add(new Position("D4"));

		assertEquals(expected, result);

		// when
		result = pawn.eligibleMoves(new Position("A1"));

		// then
		expected = new HashSet<>();
		expected.add(new Position("B2"));

		assertEquals(expected, result);

	}
}
