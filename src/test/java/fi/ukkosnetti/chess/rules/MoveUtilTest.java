package fi.ukkosnetti.chess.rules;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;


public class MoveUtilTest {

	@Test
	public void filtersOutNullMoves() {
		assertTrue(MoveUtil.filterAndTransformMoves(Arrays.asList(null, null)).isEmpty());
	}
}
