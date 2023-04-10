package generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Stack;

public class Generator {
  private Stack<String> programStack = new Stack<String>();

  // programStack will contain the tokens in postfix notation, for example:
  // [9, 1, []=, ;;], meaning m[3+6] = 1 before optimization.
  public void addInstruction(String inst){
    programStack.push(inst);
  }

  public void replaceExpression(int value){
    programStack.pop();
    programStack.pop();
    programStack.pop();
    programStack.push(Integer.toString(value));
  }

  public String toString(){
    return programStack.toString();
  }

  public void generateByteCode() throws FileNotFoundException{
    StringBuilder bytecode = new StringBuilder();
    Iterator<String> itr = programStack.iterator();

    while(itr.hasNext()){
      bytecode.append(Instruction.toOpCode(itr.next()));
    }
    PrintStream out = new PrintStream(new FileOutputStream("output.bytecode"));
    out.print(bytecode);
    out.close();
    System.out.println("Bytecode generated: output.bytecode");
  }
}
