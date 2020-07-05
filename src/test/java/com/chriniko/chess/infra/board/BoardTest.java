package com.chriniko.chess.infra.board;

import com.chriniko.chess.infra.path.Path;
import com.chriniko.chess.infra.piece.PieceColor;
import com.chriniko.chess.infra.piece.Knight;
import com.chriniko.chess.infra.piece.Piece;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	@Test
	public void setPieceAndRemovePieceWorksAsExpected() {

		// given
		Board board = new Board();

		// when - then
		assertTrue(board.isEmpty());

		// when
		board.setPiece(new TestPiece(), new Position("a1"));

		// then
		assertFalse(board.isEmpty());
		assertEquals(1, board.getPiecesOnBoard());

		// when
		board.removePiece(new Position("a1"));

		// then
		assertTrue(board.isEmpty());
		assertEquals(0, board.getPiecesOnBoard());

		// when
		char[] elems = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		for (char elem : elems) {
			for (int k = 1; k <= 8; k++) {
				board.setPiece(new TestPiece(), new Position("" + elem + k));
			}
		}

		assertFalse(board.isEmpty());
		assertEquals(8 * 8, board.getPiecesOnBoard());

	}

	@Test
	public void getPieceWorksAsExpected() {

		// given
		Board board = new Board();

		// when
		Piece result = board.getPiece(new Position("A1"));

		// then
		assertNull(result);

		// when
		TestPiece testPiece = new TestPiece();
		board.setPiece(testPiece, new Position("A1"));
		result = board.getPiece(new Position("A1"));

		// then
		assertEquals(testPiece, result);

	}

	@Test
	public void calculatePathDFSWorksAsExpected() {

		// given
		Position currentPosition = new Position("B3");
		Position targetPosition = new Position("G5");
		Piece knight = new Knight(PieceColor.BLACK, "knight-black-1");
		Board board = new Board();

		board.setPiece(knight, currentPosition);

		// when
		Set<String> paths = board.calculatePathDFS(currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		Set<String> expected = new HashSet<>();
		expected.add("B3-->C5-->E6-->G5");
		expected.add("B3-->C5-->E4-->G5");

		expected.add("B3-->D4-->F3-->G5");
		expected.add("B3-->D4-->E6-->G5");

		expected.add("B3-->D2-->F3-->G5");
		expected.add("B3-->D2-->E4-->G5");

		assertEquals(expected, paths);

	}

	// --- infra ---

	private static class TestPiece extends Piece {

		private TestPiece() {
			super(PieceColor.WHITE, "test piece");
		}

		public @Override Set<Position> eligibleMoves(Position currentPosition) {
			throw new UnsupportedOperationException();
		}
	}
}
