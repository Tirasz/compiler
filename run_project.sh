#!/bin/bash

echo "## Generating java files from grammar..."
java -cp .:antlr-4.11.1-complete.jar org.antlr.v4.Tool Byte.g4

echo "## Compiling generated java files..."
javac -cp .:antlr-4.11.1-complete.jar *.java

echo "## Running the parser on input..."
java -cp .:antlr-4.11.1-complete.jar ByteParser input.txt

echo "## Running the VM on the output..."
python3 virtual_machine.py output.bytecode
