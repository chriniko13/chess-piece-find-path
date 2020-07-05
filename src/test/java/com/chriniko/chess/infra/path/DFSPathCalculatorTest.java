package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.piece.PieceColor;
import com.chriniko.chess.infra.piece.Knight;
import com.chriniko.chess.infra.piece.Pawn;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DFSPathCalculatorTest {

	@Test
	public void processWorksAsExpectedForKnightPiece() {

		// given
		DFSPathCalculator calculator = new DFSPathCalculator();
		Knight knight = new Knight(PieceColor.WHITE, "knight-white-1");

		// when
		Position currentPosition = new Position("D4");

		Position targetPosition = new Position("F8");
		Set<String> paths = calculator.process(knight, currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		Set<String> expected = new HashSet<>();
		expected.add("D4-->E6-->F8");
		assertEquals(expected, paths);

		// when
		currentPosition = new Position("A1");

		targetPosition = new Position("A3");
		paths = calculator.process(knight, currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		expected = new HashSet<>();
		expected.add("A1-->C2-->A3");
		assertEquals(expected, paths);

		// when
		currentPosition = new Position("B3");

		targetPosition = new Position("G5");
		paths = calculator.process(knight, currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		expected = new HashSet<>();
		expected.add("B3-->C5-->E6-->G5");
		expected.add("B3-->C5-->E4-->G5");

		expected.add("B3-->D4-->F3-->G5");
		expected.add("B3-->D4-->E6-->G5");

		expected.add("B3-->D2-->F3-->G5");
		expected.add("B3-->D2-->E4-->G5");

		assertEquals(expected, paths);

		// when
		currentPosition = new Position("A1");

		targetPosition = new Position("H8");
		paths = calculator.process(knight, currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		expected = new HashSet<>();
		assertEquals(expected, paths);

	}

	@Test
	public void processWorksAsExpectedForPawnPiece() {

		// given
		DFSPathCalculator calculator = new DFSPathCalculator();
		Pawn pawn = new Pawn(PieceColor.WHITE, "pawn-white-1");

		// when
		Position currentPosition = new Position("D4");

		Position targetPosition = new Position("G7");
		Set<String> paths = calculator.process(pawn, currentPosition, targetPosition).stream().map(Path::getPathStringified).collect(Collectors.toSet());

		// then
		Set<String> expected = new HashSet<>();
		expected.add("D4-->E5-->F6-->G7");
		assertEquals(expected, paths);
	}
}
