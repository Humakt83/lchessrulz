package fi.ukkosnetti.chess.rules.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;


public class RookTest {

	@Test
	public void has14MovesInCenter() {
		Piece rook = new Rook(true, new Position(4, 4));
		assertEquals(14, rook.getMoves(new Board(BoardTestUtil.createBoardWithPieces(rook), true)).size());
	}
	
	@Test
	public void has14MovesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertEquals(14, rook.getMoves(new Board(BoardTestUtil.createBoardWithPieces(rook), true)).size());
	}
	
	@Test
	public void hasOnlyTwoMovesWhenBlockedByEnemiesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertEquals(2, rook.getMoves(new Board(BoardTestUtil.createBoardWithPieces(rook, new Pawn(false, new Position(1, 0)), new Pawn(false, new Position(0, 1)), new King(true, new Position(7,7))), true)).size());
	}
	
	@Test
	public void hasNoMovesWhenBlockedByAlliesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertTrue(rook.getMoves(new Board(BoardTestUtil.createBoardWithPieces(rook, new Pawn(true, new Position(1, 0)), new Pawn(true, new Position(0, 1))), true)).isEmpty());
	}
	
}
