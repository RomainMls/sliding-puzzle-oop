SRC_ROOT := src
SRC_DIR := src/be/uliege/montefiore/oop
JAR_FILE := lib/sliding-puzzle-gui.jar
MAIN_CLASS := be.uliege.montefiore.oop.SlidingPuzzle

CP := $(SRC_ROOT):$(JAR_FILE)
SOURCES := $(shell find $(SRC_ROOT) -name "*.java")

all: compile

compile:
	javac -cp $(CP) $(SOURCES)

# use: make run ARGS="examples/klotski.spzl"
run: compile
	java -cp $(CP) $(MAIN_CLASS) $(ARGS)

klotski: compile
	java -cp $(CP) $(MAIN_CLASS) examples/klotski.spzl

15-puzzle: compile
	java -cp $(CP) $(MAIN_CLASS) examples/15-puzzle.spzl

test: compile
	java -cp $(CP) $(MAIN_CLASS) examples/test.spzl

clean:
	find $(SRC_ROOT) -type f -name "*.class" -exec rm -f {} +
