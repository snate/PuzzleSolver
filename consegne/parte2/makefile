JC = javac
CLASSES := puzzle/PuzzleSolver.java
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
default: classes
classes: $(CLASSES:.java=.class)
clean:
	rm puzzle/*.class
