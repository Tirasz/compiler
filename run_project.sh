#!/bin/bash
# Extend classpath with antlr.jar
CLASSPATH=.:$(dirname "$0")/antlr-4.11.1-complete.jar:$CLASSPATH

echo "## Generating java files from grammar..."
java -jar $(dirname "$0")/antlr-4.11.1-complete.jar Byte.g4

echo "## Compiling generated java files..."
javac Byte*.java

echo "## Running the parser on input..."
java ByteParser input.txt

echo "## Running the VM on the output..."
python virtual_machine.py output.bytecode