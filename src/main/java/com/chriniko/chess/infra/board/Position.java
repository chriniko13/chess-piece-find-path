package com.chriniko.chess.infra.board;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a position on {@link Board}.
 *
 * Eg: A5 or a5 , A or a represents the column which is translated to 0,
 *     5 represents the row which is translated to 4, so A5 takes the (0,4) place on {@link Board}
 */
public class Position {

	// Note: always stored in lower case.
	protected final String name;

	private final int x;
	private final int y;

	public Position(String name) {
		if (name == null || name.length() != 2) {
			throw new IllegalArgumentException("invalid position, correct position eg: A1, a1, H8, h8");
		}
		this.name = name.toLowerCase();
		char[] chars = this.name.toCharArray();

		validate(chars);

		x = 8 - Character.getNumericValue(chars[1]);
		y = chars[0] - 'a';
	}

	private Position(int x, int y) {
		this.x = x;
		this.y = y;

		String temp = "" + getCharForNumber(y) + (8 - x);
		this.name = temp.toLowerCase();
	}

	private char getCharForNumber(int i) {
		return (char) ('a' + i);
	}

	public static Optional<Position> create(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return Optional.empty();
		}
		return Optional.of(new Position(x, y));
	}

	private void validate(char[] chars) {
		char firstChar = chars[0];
		if (!Character.isLetter(firstChar) || firstChar < 'a' || firstChar > 'h') {
			throw new IllegalArgumentException("invalid position, first part should be a value from: [A-H]|[a-h]");
		}

		char secondChar = chars[1];
		if (Character.isLetter(secondChar)) {
			throw new IllegalArgumentException("invalid position, second part should be a value from: [1-8]");
		}
		int num = Character.getNumericValue(secondChar);
		if (num < 1 || num > 8) {
			throw new IllegalArgumentException("invalid position, second part should be a value from: [1-8]");
		}
	}

	@Override public String toString() {
		return "Position{" +
				"s='" + name + '\'' +
				", x=" + x +
				", y=" + y +
				'}';
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Position))
			return false;
		Position position = (Position) o;
		return Objects.equals(name, position.name);
	}

	@Override public int hashCode() {
		return Objects.hash(name);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}
}
