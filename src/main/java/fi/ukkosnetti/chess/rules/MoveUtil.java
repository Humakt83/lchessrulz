package fi.ukkosnetti.chess.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.rules.piece.Piece;
import fi.ukkosnetti.chess.rules.piece.PieceFactory;

public final class MoveUtil {

	private final static int MIN_COORD = 0, MAX_COORD = 7;
	
	/**
	 * Filters out illegal moves from the given moves and transforms them to list of board states
	 * @param moves to filter and transform
	 * @return list of board states moves were transformed into
	 */
	public static List<Board> filterAndTransformMoves(List<Move> moves) {
		return moves.stream()
				.filter(Objects::nonNull)
				.filter(MoveUtil::isMoveOnBoard)
				.filter(MoveUtil::moveDoesNotCollideWithOwnPiece)
				.map(MoveUtil::transformMove)
				.filter(MoveUtil::isBoardNotInMate)
				.collect(Collectors.toList());
	}
	
	/**
	 * Retuns diagonal moves for piece
	 * @param board state piece is in
	 * @param piece that moves are returned for
	 * @return list of diagonal moves for the piece
	 */
	public static List<Move> getDiagonalMoves(Board board, Piece piece) {
		List<Move> moves = new ArrayList<>();
		moves.addAll(getMovesUntilBlocked(board, 1, 1, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, 1, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, 1, piece));
		return moves;
	}
	
	/**
	 * Retuns horizontal and vertical moves for piece
	 * @param board state piece is in
	 * @param piece that moves are returned for
	 * @return list of horizontal and diagonal moves
	 */
	public static List<Move> getHorizontalAndVerticalMoves(Board board, Piece piece) {
		List<Move> moves = new ArrayList<>();
		moves.addAll(getMovesUntilBlocked(board, 0, 1, piece));
		moves.addAll(getMovesUntilBlocked(board, 0, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, 1, 0, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, 0, piece));
		return moves;
	}
	
	/**
	 * Checks whether given position is inside boundaries of board
	 * @param pos position to check
	 * @return true when position is inside board, false otherwise
	 */
	public static boolean isPositionInsideBoard(Position pos) {
		return pos.x >= MIN_COORD && pos.x <= MAX_COORD && pos.y >= MIN_COORD && pos.y <= MAX_COORD;
	}
	
	/**
	 * Returns all the white or black pieces of the board
	 * @param board where the information of pieces are retrieved from
	 * @param whitePieces whether to return white or black pieces
	 * @return list of pieces
	 */
	public static List<Piece> getPieces(Board board, boolean whitePieces) {
		List<Piece> pieces = new ArrayList<>();
		for (int y = 0; y < board.board.length ; y++) {
			for (int x = 0; x < board.board[y].length; x++) {
				Position position = new Position(x, y);
				int slot = board.getSlot(position);
				if ((whitePieces && slot > 0) || (!whitePieces && slot < 0)) {
					pieces.add(PieceFactory.createPiece(slot, position));
				}
			}
		}
		return pieces;
	}
	
	/**
	 * Checks whether current board position would cause a mate
	 * @param board to evaluate
	 * @return true if mate is not the current state
	 */
	public static boolean isBoardNotInMate(final Board board) {
		boolean noMate = true;
		if (!board.isDoNotCheckForMate()) {
			final int kingToFind = board.turnOfWhite ? -6 : 6;
			board.setDoNotCheckForMate(true);
			noMate = !getPieces(board, board.turnOfWhite)
				.stream()
				.map(piece -> piece.getMoves(board))
				.flatMap(l -> l.stream())
				.filter(futureBoard -> Stream.of(futureBoard.board).flatMap(Stream::of).filter(i -> i == kingToFind).findAny().orElse(null) == null)
				.findAny()
				.isPresent();
		}
		return noMate;
	}
	
	/**
	 * Transforms move to board state
	 * @param move to transform
	 * @return board that is the result of transformed move
	 */
	public static Board transformMove(Move move) {
		Integer[][] b = copyBoard(move.originalBoard.board);
		b[move.position.y][move.position.x] = move.piece.getPieceValue();
		b[move.original.y][move.original.x] = 0;
		Board board = new Board(b, !move.originalBoard.turnOfWhite, move, move.newCastlingState);
		if (move.consumer != null) move.consumer.accept(board);
		board.setDoNotCheckForMate(move.originalBoard.isDoNotCheckForMate());
		return board;
	}

	private static boolean isMoveOnBoard(Move move) {
		return isPositionInsideBoard(move.position);
	}
	
	private static boolean moveDoesNotCollideWithOwnPiece(Move move) {
		boolean whitePiece = move.piece.whitePiece;
		int slot = move.originalBoard.getSlot(move.position);
		return (slot <= 0 && whitePiece) || (slot >= 0 && !whitePiece);
	}

	private static Integer[][] copyBoard(Integer[][] board) {
		Integer[][] copy = new Integer[MAX_COORD + 1][MAX_COORD + 1];
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				copy[y][x] = board[y][x];
			}
		}
		return copy;
	}

	private static List<Move> getMovesUntilBlocked(Board board, int xModifier, int yModifier, Piece piece) {
		List<Move> moves = new ArrayList<>();
		boolean blocked = false;
		Position newPosition = piece.position.newPosition(xModifier, yModifier);
		while (MoveUtil.isPositionInsideBoard(newPosition) && !blocked) {
			moves.add(new Move(piece.position, newPosition, piece, board));
			blocked = blocked || board.getSlot(newPosition) != 0;
			newPosition = newPosition.newPosition(xModifier, yModifier);
		}
		return moves;
	}
	


}
