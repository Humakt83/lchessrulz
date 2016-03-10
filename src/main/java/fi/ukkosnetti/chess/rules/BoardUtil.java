package fi.ukkosnetti.chess.rules;

import java.util.Arrays;

import fi.ukkosnetti.chess.dto.Board;

public final class BoardUtil {

	public static boolean hasQueen(Board board, boolean whiteQueen) {
		final int pieceToFind = whiteQueen ? 5: -5;
		return Arrays.asList(board.board).stream()
				.flatMap(row -> Arrays.asList(row).stream())
				.filter(slot -> slot == pieceToFind)
				.findAny()
				.isPresent();
	}
	
	public static Integer[][] createStartingBoard() {
		return new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
	}
	
}
