package fi.ukkosnetti.chess.rules.piece;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;


public class KnightTest {

	@Test
	public void hasEightMoves() {
		Piece knight = new Knight(true, new Position(4, 4));
		assertEquals(8, knight.getMoves(new Board(BoardTestUtil.createBoardWithPieces(knight), true)).size());
	}
	
	@Test
	public void hasOnlyTwoMovesInCorner() {
		Piece knight = new Knight(true, new Position(0, 0));
		assertEquals(2, knight.getMoves(new Board(BoardTestUtil.createBoardWithPieces(knight), true)).size());
	}
}
