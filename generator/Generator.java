package generator;

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


}
