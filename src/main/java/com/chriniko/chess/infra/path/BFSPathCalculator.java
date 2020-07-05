package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.piece.Piece;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Calculates the path by using breadth-first search approach.
 *
 * Breadth-first search always returns the shortest path in
 * an undirected, unweighted graph.
 *
 * The claim for BFS is that the first time a node
 * has being discovered during the traversal,
 * that distance from the source would give us the shortest path.
 *
 * Time complexity: O(n^2)
 *
 */
public class BFSPathCalculator implements PathCalculator {

	private static final int MOVES_LIMIT = 3;

	@Override public Set<Path> process(Piece piece, Position start, Position end) {

		long startTime = System.currentTimeMillis();

		Set<Path> pathResults = new LinkedHashSet<>();
		Path path = new Path();
		process(pathResults, piece, start, end, path);

		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println(this.getClass().getSimpleName() + "#process took: " + totalTime + " ms.");

		return pathResults;

	}

	private void process(Set<Path> paths,
			Piece piece,
			Position start, Position end,
			Path path) {

		Map<Position, Path> pathsByPosition = new LinkedHashMap<>();
		pathsByPosition.put(start, path);

		Queue<Position> q = new LinkedList<>();
		q.add(start);

		Set<Position> visited = new LinkedHashSet<>();

		while (!q.isEmpty()) {

			Position head = q.poll();

			Path p = pathsByPosition.get(head);

			if (p.size() > MOVES_LIMIT) {
				continue;
			}

			p.addPosition(head);

			if (head.equals(end)) {
				paths.add(p);
				continue;
			}

			Set<Position> eligibleMoves = piece.eligibleMoves(head);
			for (Position eligibleMove : eligibleMoves) {

				if (visited.contains(eligibleMove))
					continue;

				visited.add(eligibleMove);
				q.add(eligibleMove);

				pathsByPosition.put(eligibleMove, new Path(p));
			}

		}

	}
}
