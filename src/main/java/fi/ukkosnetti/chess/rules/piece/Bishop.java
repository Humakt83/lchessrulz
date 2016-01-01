package fi.ukkosnetti.chess.rules.piece;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.rules.MoveUtil;

public class Bishop extends Piece {

	public Bishop(boolean whitePiece, Position position) {
		super(whitePiece, position, 3, 95);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		return MoveUtil.filterAndTransformMoves(MoveUtil.getDiagonalMoves(board, this));
	}

	@Override
	protected int getPositionModifierForEvaluation(Board board) {
		return MoveUtil.getDiagonalMoves(board, this).size();
	}

}
