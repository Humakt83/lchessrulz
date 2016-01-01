package fi.ukkosnetti.chess.rules.piece;

import fi.ukkosnetti.chess.dto.Board;

public interface Evaluatable {

	int getEvaluationValue(Board board);
}
