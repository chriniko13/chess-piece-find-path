package com.chriniko.chess.infra.path;

import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.piece.Piece;

import java.util.Set;

public interface PathCalculator {

	Set<Path> process(Piece p, Position start, Position end);

}
