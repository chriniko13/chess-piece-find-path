package com.chriniko.chess.infra.piece;

import com.chriniko.chess.infra.board.Position;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class KnightTest {

	@Test
	public void eligibleMovesWorksAsExpected() {

		// given
		Piece knight = new Knight(PieceColor.WHITE, "knight-1-white");
		Position currentPosition = new Position("E4");

		// when
		Set<Position> result = knight.eligibleMoves(currentPosition);

		// then
		Set<Position> expected = new HashSet<>();
		expected.add(new Position("D6"));
		expected.add(new Position("F6"));

		expected.add(new Position("C5"));
		expected.add(new Position("C3"));

		expected.add(new Position("D2"));
		expected.add(new Position("F2"));

		expected.add(new Position("G3"));
		expected.add(new Position("G5"));

		assertEquals(expected, result);

		// when
		result = knight.eligibleMoves(new Position("A8"));

		// then
		expected = new HashSet<>();
		expected.add(new Position("B6"));
		expected.add(new Position("C7"));

		assertEquals(expected, result);

		// when
		result = knight.eligibleMoves(new Position("B2"));

		// then
		expected = new HashSet<>();
		expected.add(new Position("A4"));
		expected.add(new Position("C4"));
		expected.add(new Position("D3"));
		expected.add(new Position("D1"));

		assertEquals(expected, result);

	}
}
