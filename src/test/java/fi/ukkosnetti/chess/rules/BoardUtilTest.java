package fi.ukkosnetti.chess.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;

public class BoardUtilTest {

	@Test
	public void boardHasWhiteQueen() {
		assertTrue(BoardUtil.hasQueen(new Board(BoardTestUtil.createStartingBoard(), true), true));
	}
	
	@Test
	public void boardHasBlackQueen() {
		assertTrue(BoardUtil.hasQueen(new Board(BoardTestUtil.createStartingBoard(), true), false));
	}
	
	@Test
	public void boardDoesNotHaveWhiteQueen() {
		assertFalse(BoardUtil.hasQueen(new Board(BoardTestUtil.createEmptyBoard(), true), true));
	}
	
	@Test
	public void boardDoesNotHaveBlackQueen() {
		assertFalse(BoardUtil.hasQueen(new Board(BoardTestUtil.createEmptyBoard(), true), false));
	}
	
}
