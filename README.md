# Authors
- Malaise Romain
- Renard Baptiste

# Sliding Puzzle OOP

This repository contains a Simple Java object-oriented programming project implementing a sliding puzzle game, which was our first java program project. The program reads a puzzle specification file, builds the corresponding board, displays it through the provided graphical interface, and lets the player move pieces until the goal is reached.

The project was made for an OOP course, with a focus on class design, encapsulation, model consistency, file parsing and GUI integration.

## Features

- Object-oriented model for puzzles, pieces, coordinates and movements.
- Support for goal pieces and goal positions.
- Specification file parser for `.spzl` puzzle files.
- Graphical display using the provided GUI jar.
- Move validation with collision and boundary checks.
- Example puzzles included: Klotski, 15-puzzle and a small test puzzle.

## Repository Structure

- `src/`: Java source code.
- `src/be/uliege/montefiore/oop/model/`: puzzle model, pieces, coordinates and movement logic.
- `src/be/uliege/montefiore/oop/reader/`: `.spzl` specification file reader.
- `src/be/uliege/montefiore/oop/gui/`: wrapper around the provided GUI.
- `examples/`: example puzzle specification files.
- `lib/`: provided GUI jar.
- `docs/brief.pdf`: project statement.

## Build

Compile the project with:

```bash
make
```

Clean generated `.class` files:

```bash
make clean
```

## Run

Run one of the predefined examples:

```bash
make klotski
make 15-puzzle
make test
```

Run a custom puzzle file:

```bash
make run ARGS="examples/klotski.spzl"
```

The program expects a `.spzl` file describing the board size, pieces and goal pieces.

## Example Files

- `examples/klotski.spzl`: Klotski-style puzzle.
- `examples/15-puzzle.spzl`: classic 15-puzzle.
- `examples/test.spzl`: small test puzzle.

## Note

This is a student project. The goal is mainly to demonstrate object-oriented design and clean separation between the puzzle model, file reader and graphical interface.
