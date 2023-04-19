# compiler
## (Assignment) Bytecode generation and execution  
As input, the program receives integer expressions with the five basic operations (addition, subtraction, multiplication, division integer and remainder), exponentiation (to a non-negative exponent), bracketing and value assignment, and memory available as m[x] (initially, each memory cell is 0). Include a read(x) instruction that reads the value of the variable m[x]. From this, generate your own bytecode (bytecode is a general term here, not (exclusively) Java Bytecode) using ANTLR grammar. You have a free hand in the specification of the bytecode (what opcode means what instruction), and also in the language of the virtual machine that runs it -- i.e. it does not have to be written in ANTLR, but it must be bytecode in nature (assembly-like with linear memory or registers, stack machine, etc.; either concrete assembly or java bytecode). The point is that the generated code, when executed on the virtual machine (or even directly), will print the values of all expressions to the console. Optimization can be done during the "compilation".

## Running: 
Have java and python installed.  
Download and copy the [ANTLR 4 jar](http://www.antlr.org/download/antlr-4.11.1-complete.jar) into the project folder.  
Run the `run_project` script.  
