package fi.ukkosnetti.chess.test.util;

import fi.ukkosnetti.chess.rules.BoardUtil;
import fi.ukkosnetti.chess.rules.piece.Piece;

public class BoardTestUtil {

	public static Integer[][] createEmptyBoard() {
		return new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
	}
	
	public static Integer[][] createStartingBoard() {
		return BoardUtil.createStartingBoard();
	}

	public static Integer[][] createBoardWithPieces(Piece... pieces) {
		Integer[][] board = createEmptyBoard();
		for (Piece piece : pieces) {
			board[piece.position.y][piece.position.x] = piece.getPieceValue();
		}
		return board;
	}
}
