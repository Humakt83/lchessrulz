package fi.ukkosnetti.chess.rules.piece;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.rules.MoveUtil;

public class Queen extends Piece {

	public Queen(boolean whitePiece, Position position) {
		super(whitePiece, position, 5, 240);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Move> moves = MoveUtil.getHorizontalAndVerticalMoves(board, this);
		moves.addAll(MoveUtil.getDiagonalMoves(board, this));
		return MoveUtil.filterAndTransformMoves(moves);
	}

	@Override
	protected int getPositionModifierForEvaluation(Board board) {
		return MoveUtil.getHorizontalAndVerticalMoves(board, this).size()
				+ MoveUtil.getDiagonalMoves(board, this).size();
	}

}
