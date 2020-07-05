package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.piece.Piece;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Calculates the path by using depth-first search approach (a specialized version of backtracking technique).
 * (One starts at the root (selecting some node as the root in the graph case) and
 * explores as far as possible along each branch before backtracking.)
 *
 *
 *
 * The time complexity is: O(b^m) where b is the branching factor and m is the maximal depth of a leaf node.
 *
 * For example, for knight piece, a knight can possible execute 8 moves at most, and also the maximal depth of a leaf node, at worst, can be
 * n^2, so the time complexity would be that of: O(8^(n^2))
 *
 */
public class DFSPathCalculator implements PathCalculator {

	private static final int MOVES_LIMIT = 3;

	@Override public Set<Path> process(Piece piece, Position start, Position end) {
		long startTime = System.currentTimeMillis();

		Set<Path> pathResults = new LinkedHashSet<>();
		Path path = new Path();
		process(pathResults, piece, start, end, 0, path);

		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println(this.getClass().getSimpleName() + "#process took: " + totalTime + " ms.");

		return pathResults;
	}

	private void process(Set<Path> paths,
			Piece piece,
			Position curPosition, Position endPosition,
			int movesCounter, Path ongoingPath) {

		if (movesCounter > MOVES_LIMIT) {
			return;
		}

		ongoingPath.addPosition(curPosition);

		if (curPosition.equals(endPosition)) {
			paths.add(ongoingPath);
			return;
		}

		Set<Position> eligibleMoves = piece.eligibleMoves(curPosition);
		for (Position eligibleMove : eligibleMoves) {

			Path p = new Path(ongoingPath);

			process(paths, piece,
					eligibleMove, endPosition,
					movesCounter + 1, p
			);
		}
	}

}
