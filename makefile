all: compile

compile:
	javac -cp .:sliding-puzzle-gui.jar be/uliege/montefiore/oop/*.java

run: 
	java -cp .:sliding-puzzle-gui.jar be.uliege.montefiore.oop.Main

clean:
	rm -f be.uliege.montefiore.oop/**/*.class