[![Build Status](https://travis-ci.org/Humakt83/lchessrulz.svg?branch=master)](https://travis-ci.org/Humakt83/lchessrulz)

# lchessrulz

Chess rules library written with Java 8.

### Usage

Starting board can be created using `BoardUtil.createStartingBoard()`.

Moves can be generated for each piece using `getPieces`-method in MoveUtil by passing a Board-object to it and then asking each piece for their individual moves.

Example that will fetch all possible starting moves (wrapped inside Board objects) for white player:

```
final Board board = BoardUtil.createStartingBoard();
List<Board> boardStatesOfMoves = MoveUtil.getPieces(board, true)
	.stream()
	.map(piece -> piece.getMoves(board))
	.flatMap(move -> move.stream())
	.collect(Collectors.toList());
```


