package fi.ukkosnetti.chess.rules.piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.CastlingState.CastlingBlocker;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.MoveBuilder;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.rules.BoardUtil;
import fi.ukkosnetti.chess.rules.MoveUtil;

public class King extends Piece {

	public King(boolean whitePiece, Position position) {
		super(whitePiece, position, 6, 5000);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Move> moves = getKingBasicMoves(board);
		if (!kingIsThreatened(board)) moves.addAll(getCastlingMoves(board));
		return MoveUtil.filterAndTransformMoves(moves);
	}

	private boolean kingIsThreatened(Board board) {
		return castlingMoveCausesMate(Arrays.asList(new MoveBuilder(position, position, this, board).build()));
	}

	private List<Move> getCastlingMoves(Board board) {
		List<Move> castlingMoves = new ArrayList<>();
		if (leftCastlingMoveIsPossible(board) && !leftCastlingMoveCausesMate(board)) {
			castlingMoves.add(new MoveBuilder(position, position.newPosition(-2, 0), this, board)
				.addCastlingBlocker(whitePiece ? CastlingBlocker.WHITE_KING_MOVED : CastlingBlocker.BLACK_KING_MOVED)
				.setConsumer(getLeftRookMover())
				.build());
		}
		if (rightCastlingMoveIsPossible(board) && !rightCastlingMoveCausesMate(board)) {
			castlingMoves.add(new MoveBuilder(position, position.newPosition(2, 0), this, board)
				.addCastlingBlocker(whitePiece ? CastlingBlocker.WHITE_KING_MOVED : CastlingBlocker.BLACK_KING_MOVED)
				.setConsumer(getRightRookMover())
				.build());
		}
		return castlingMoves;
	}

	private boolean leftCastlingMoveCausesMate(Board board) {
		return castlingMoveCausesMate(Arrays.asList(new MoveBuilder(position, position.newPosition(-1, 0), this, board).build(),
				new MoveBuilder(position, position.newPosition(-2, 0), this, board).build(),
				new MoveBuilder(position, position.newPosition(-3, 0), this, board).build()));
	}
	
	private boolean rightCastlingMoveCausesMate(Board board) {
		return castlingMoveCausesMate(Arrays.asList(new MoveBuilder(position, position.newPosition(1, 0), this, board).build(),
				new MoveBuilder(position, position.newPosition(2, 0), this, board).build()));
	}

	private boolean castlingMoveCausesMate(List<Move> spotsToCheck) {
		return spotsToCheck.stream()
			.map(move -> MoveUtil.transformMove(move))
			.filter(boardOfMove -> !MoveUtil.isBoardNotInMate(boardOfMove))
			.findAny().isPresent();
	}

	private Consumer<Board> getLeftRookMover() {
		return board -> {
			board.board[position.y][position.x - 1] = whitePiece ? 4 : -4;
			board.board[position.y][position.x - 4] = 0;
		};
	}
	
	private Consumer<Board> getRightRookMover() {
		return board -> {
			board.board[position.y][position.x + 1] = whitePiece ? 4 : -4;
			board.board[position.y][position.x + 3] = 0;
		};
	}

	private boolean leftCastlingMoveIsPossible(Board board) {
		List<CastlingBlocker> blockers = board.castlingState.blockers;
		Position rookPosition = position.newPosition(-4, 0);
		if (!MoveUtil.isPositionInsideBoard(rookPosition)  || board.getSlot(position.newPosition(-1, 0)) != 0 
				|| board.getSlot(position.newPosition(-2,  0)) != 0 || board.getSlot(position.newPosition(-3,  0)) != 0) return false; 
		int rookSlot = board.getSlot(rookPosition);
		if (whitePiece) return !blockers.contains(CastlingBlocker.WHITE_KING_MOVED) && !blockers.contains(CastlingBlocker.WHITE_LEFT_ROOK_MOVED) && rookSlot == 4; 
		return !blockers.contains(CastlingBlocker.BLACK_KING_MOVED) && !blockers.contains(CastlingBlocker.BLACK_LEFT_ROOK_MOVED) && rookSlot == -4; 
	}
	
	private boolean rightCastlingMoveIsPossible(Board board) {
		List<CastlingBlocker> blockers = board.castlingState.blockers;
		Position rookPosition = position.newPosition(3, 0);
		if (!MoveUtil.isPositionInsideBoard(rookPosition) || board.getSlot(position.newPosition(1, 0)) != 0 || board.getSlot(position.newPosition(2,  0)) != 0) return false; 
		int rookSlot = board.getSlot(rookPosition);
		if (whitePiece) return !blockers.contains(CastlingBlocker.WHITE_KING_MOVED) && !blockers.contains(CastlingBlocker.WHITE_RIGHT_ROOK_MOVED) && rookSlot == 4; 
		return !blockers.contains(CastlingBlocker.BLACK_KING_MOVED) && !blockers.contains(CastlingBlocker.BLACK_RIGHT_ROOK_MOVED) && rookSlot == -4; 
	}

	private List<Move> getKingBasicMoves(Board board) {
		return Arrays.asList(position.newPosition(1, 0), position.newPosition(1, 1), position.newPosition(0, 1), position.newPosition(-1, 0), 
				position.newPosition(-1, 1), position.newPosition(1, -1), position.newPosition(0, -1), position.newPosition(-1, -1))
				.stream().map(p -> getKingBasicMove(p, board))
				.collect(Collectors.toList());
	}
	
	private Move getKingBasicMove(Position newPosition, Board board) {
		return new MoveBuilder(position, newPosition, this, board).addCastlingBlocker(whitePiece ? CastlingBlocker.WHITE_KING_MOVED : CastlingBlocker.BLACK_KING_MOVED).build();
	}

	@Override
	protected int getPositionModifierForEvaluation(Board board) {
		int positionalValue = 0;
		if (BoardUtil.hasQueen(board, !whitePiece)) {
			positionalValue += whitePiece && position.y == 7 || !whitePiece && position.y == 0 ? 5 : 0;
			positionalValue += position.x > 5 || position.x < 3 ? 5 : 0;
		}
		return positionalValue;
	}

}
