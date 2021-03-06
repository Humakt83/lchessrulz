package fi.ukkosnetti.chess.rules.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;


public class BishopTest {

	@Test
	public void has13MovesInCenter() {
		Piece bishop = new Bishop(true, new Position(4, 4));
		assertEquals(13, bishop.getMoves(new Board(BoardTestUtil.createBoardWithPieces(bishop), true)).size());
	}
	
	@Test
	public void hasSevenMovesInCorner() {
		Piece bishop = new Bishop(true, new Position(0, 0));
		assertEquals(7, bishop.getMoves(new Board(BoardTestUtil.createBoardWithPieces(bishop), true)).size());
	}
	
	@Test
	public void hasOnlyOneMoveWhenBlockedByEnemyInCorner() {
		Piece bishop = new Bishop(true, new Position(0, 0));
		assertEquals(1, bishop.getMoves(new Board(BoardTestUtil.createBoardWithPieces(bishop, new Pawn(false, new Position(1, 1))), true)).size());
	}
	
	@Test
	public void hasNoMovesWhenBlockedByAllyInCorner() {
		Piece bishop = new Bishop(true, new Position(0, 0));
		assertTrue(bishop.getMoves(new Board(BoardTestUtil.createBoardWithPieces(bishop, new Pawn(true, new Position(1, 1))), true)).isEmpty());
	}
	
}
