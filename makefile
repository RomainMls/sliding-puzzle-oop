SRC_DIR := be/uliege/montefiore/oop
JAR_FILE := sliding-puzzle-gui.jar
MAIN_CLASS := be.uliege.montefiore.oop.Main

CP := .:$(JAR_FILE)

all: compile

compile:
	javac -cp $(CP) $(SRC_DIR)/*.java

# use ' make run ARGS="filename" '
run: compile
	java -cp $(CP) $(MAIN_CLASS) $(ARGS)

klotski: compile
	java -cp $(CP) $(MAIN_CLASS) klotski.spzl

clean:
	find $(SRC_DIR) -type f -name "*.class" -exec rm -f {} +