package fi.ukkosnetti.chess.rules;

import java.util.Arrays;

import fi.ukkosnetti.chess.dto.Board;

public class BoardUtil {

	public static boolean hasQueen(Board board, boolean whiteQueen) {
		final int pieceToFind = whiteQueen ? 5: -5;
		return Arrays.asList(board.board).stream()
				.flatMap(row -> Arrays.asList(row).stream())
				.filter(slot -> slot == pieceToFind)
				.findAny()
				.isPresent();
	}
	
}
