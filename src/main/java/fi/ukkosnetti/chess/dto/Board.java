package fi.ukkosnetti.chess.dto;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Main class of interest. 
 * Represents board and the current state of the game.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Board {

	/**
	 * All slots in the board. Positive number represents slot is occupied by a white piece while 
	 * negative represents a black piece. Zero represents an empty slot.
	 */
	public final Integer[][] board;

	public final Boolean turnOfWhite;
	
	/**
	 * Move made previously. Useful overall and vital for handling en passant moves.
	 */
	public final Move lastMove;
	
	/**
	 * Contains the information what moves have been made in the game that would prevent castling.
	 */
	public final CastlingState castlingState;
	
	@JsonIgnore
	private boolean doNotCheckForMate = false;
	
	@JsonIgnore
	private Long value;
	
	@JsonIgnore
	private Long evaluatedValue;

	public Board(Integer[][] board, Boolean turnOfWhite, Move lastMove, CastlingState castlingState) {
		this.board = board;
		this.turnOfWhite = turnOfWhite;
		this.lastMove = lastMove;
		this.castlingState = castlingState;
	}
	
	public Board(Integer[][] board, Boolean turnOfWhite) {
		this(board, turnOfWhite, null, new CastlingState());
	}

	@SuppressWarnings("unused")
	private Board() {
		this(null, null, null, null);
	}
	
	public int getSlot(Position pos) {
		return board[pos.y][pos.x];
	}
	
	public boolean isDoNotCheckForMate() {
		return doNotCheckForMate;
	}
	
	public void setDoNotCheckForMate(boolean doNotCheckForMate) {
		this.doNotCheckForMate = doNotCheckForMate;
	}
	
	public Long getValue() {
		return value;
	}
	
	public void setValue(Long value) {
		this.value = value;
	}
	
	public Long getEvaluatedValue() {
		return evaluatedValue;
	}
	
	public void setEvaluatedValue(Long evaluatedValue) {
		this.evaluatedValue = evaluatedValue;
	}

	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + ", turnOfWhite=" + turnOfWhite + ", lastMove=" + lastMove + ", castlingState=" + castlingState + "]";
	}
	
	

}
