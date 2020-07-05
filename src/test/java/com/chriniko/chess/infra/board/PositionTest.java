package com.chriniko.chess.infra.board;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {

	@Test
	public void representationWorksAsExpected() {

		// when
		Position position = new Position("h8");

		// then
		assertEquals(0, position.getX());
		assertEquals(7, position.getY());

		// when
		position = new Position("H8");

		// then
		assertEquals(0, position.getX());
		assertEquals(7, position.getY());

		// when
		position = new Position("a1");

		// then
		assertEquals(7, position.getX());
		assertEquals(0, position.getY());

		// when
		position = new Position("g5");

		// then
		assertEquals(3, position.getX());
		assertEquals(6, position.getY());
	}

	@Test
	public void validationFirstPartWorksAsExpected() {

		try {
			// when
			new Position("14A52");
		} catch (IllegalArgumentException ex) { // then
			assertEquals("invalid position, correct position eg: A1, a1, H8, h8", ex.getMessage());
		}
	}

	@Test
	public void validationSecondPartWorksAsExpected() {
		try {
			// when
			new Position("AA");
		} catch (IllegalArgumentException ex) { // then
			assertEquals("invalid position, second part should be a value from: [1-8]", ex.getMessage());
		}
	}

	@Test
	public void createWorksAsExpected() {

		// when
		Optional<Position> position = Position.create(0, 0);

		// then
		assertTrue(position.isPresent());
		assertEquals(new Position("A8"), position.get());

		// when
		position = Position.create(7, 7);

		// then
		assertTrue(position.isPresent());
		assertEquals(new Position("H1"), position.get());
		assertEquals(new Position("h1"), position.get());

	}

}
