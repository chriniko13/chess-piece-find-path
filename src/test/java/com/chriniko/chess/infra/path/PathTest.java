package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.path.Path;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathTest {

	@Test
	public void addPositionAndGetPathStringifiedWorksAsExpected() {

		// given
		Path p = new Path();

		// when
		p.addPosition(new Position("A2"));
		p.addPosition(new Position("C3"));
		p.addPosition(new Position("E4"));

		// then
		assertEquals("A2-->C3-->E4", p.getPathStringified());
	}

	@Test
	public void snapshotWorksAsExpected() {

		// given
		Path p = new Path();
		p.addPosition(new Position("a1"));
		p.addPosition(new Position("a2"));

		// when
		String snapshotPath = new Path(p).getPathStringified();
		p.addPosition(new Position("a3"));
		p.addPosition(new Position("a4"));
		String path = p.getPathStringified();

		// then
		assertEquals("A1-->A2", snapshotPath);
		assertEquals("A1-->A2-->A3-->A4", path);

	}

}
