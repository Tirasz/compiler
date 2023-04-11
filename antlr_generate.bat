@ ECHO OFF
Rem Extend classpath with antlr.jar 
SET CLASSPATH=.;%~dp0\antlr-4.11.1-complete.jar;%CLASSPATH%

ECHO ## Generating java files from grammar...
java org.antlr.v4.Tool Byte.g4

ECHO ## Compiling generated java files...
javac Byte*.java

ECHO ## Running the parser on input...
java ByteParser input.txt

ECHO ## Running the VM on the output...
python virtual_machine.py output.bytecode