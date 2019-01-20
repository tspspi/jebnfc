JAVAC=javac -Xlint:unchecked
JARCMD=jar
RMCMD=del

SRCFILES=./at/tspi/jcliargs/CliOption.java \
		./at/tspi/jcliargs/CliOptionBoolean.java \
		./at/tspi/jcliargs/CliOptionInteger.java \
		./at/tspi/jcliargs/CliOptionString.java \
		./at/tspi/jcliargs/CliParser.java \
		./at/tspi/jcliargs/CliParserAlreadyDefinedException.java \
		./at/tspi/jcliargs/CliParserException.java \
		./at/tspi/jcliargs/CliParserUnknownOptionException.java

CLASSFILES=./at/tspi/jcliargs/CliOption.class \
		./at/tspi/jcliargs/CliOptionBoolean.class \
		./at/tspi/jcliargs/CliOptionInteger.class \
		./at/tspi/jcliargs/CliOptionString.class \
		./at/tspi/jcliargs/CliParser.class \
		./at/tspi/jcliargs/CliParserAlreadyDefinedException.class \
		./at/tspi/jcliargs/CliParserException.class \
		./at/tspi/jcliargs/CliParserUnknownOptionException.class

JARFILE=./bin/jcliargs.jar

MANIFESTFILE=./JarManifest.mf

EXAMPLE_1_SOURCES=./example/simple/testprog.java

EXAMPLE_1_CLASSES=./example/simple/testprog.class

all: build package tests examples

build: ${SRCFILES}

	${JAVAC} ${SRCFILES}

package: ${CLASSFILES}

	${JARCMD} cvmf ${MANIFESTFILE} ${JARFILE} ${CLASSFILES}

examples: package examplesimple1

examplesimple1: package ${EXAMPLE_1_SOURCES}

	${JAVAC} -cp ${JARFILE} ${EXAMPLE_1_SOURCES}

tests: package

clean:

	-${RMCMD} ${CLASSFILES}
	-${RMCMD} ${EXAMPLE_1_CLASSES}


.PHONY: clean