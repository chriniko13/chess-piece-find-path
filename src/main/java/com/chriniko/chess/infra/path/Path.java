package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Path {

	private final Set<Position> positions;

	public Path() {
		positions = new LinkedHashSet<>();
	}

	// Note: deep-copy.
	public Path(Path other) {
		this.positions = new LinkedHashSet<>();

		for (Position otherPosition : other.positions) {
			this.positions.add(new Position(otherPosition.getName()));
		}
	}

	public void addPosition(Position p) {
		positions.add(p);
	}

	public String getPathStringified() {
		return positions
				.stream()
				.map(Position::getName)
				.map(String::toUpperCase)
				.collect(Collectors.joining("-->"));
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Path))
			return false;
		Path path = (Path) o;
		return Objects.equals(positions, path.positions);
	}

	@Override public int hashCode() {
		return Objects.hash(positions);
	}

	public int size() {
		return positions.size();
	}

	public Set<Position> getPositions() {
		return Collections.unmodifiableSet(positions);
	}
}
