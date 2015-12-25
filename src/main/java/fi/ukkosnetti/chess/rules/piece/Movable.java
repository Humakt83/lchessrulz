package fi.ukkosnetti.chess.rules.piece;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;

public interface Movable {

	List<Board> getMoves(final Board board);
	
	public boolean canEatKing(final Board board);
}
