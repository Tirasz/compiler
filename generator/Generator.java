package generator;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {
  private Stack<String> programStack = new Stack<String>();
  public int lines = 0;

  // programStack will contain the tokens in postfix notation, for example:
  // [9, 1, []=, ;;], meaning m[3+6] = 1 before optimization.
  public void addInstruction(String inst){
    System.out.println("PUSHING: " + inst);
    programStack.push(inst);
    //tryEvalTop();
  }

  private void tryEvalTop(){
    // Check if the top instruction can be evaluated (basically not a constant or memory access / assignment)
    Instruction instruction = Instruction.fromString(programStack.peek());
    if(!instruction.isEvaluable())
      return;
    
    // Instead of emptyStackException :)
    if(programStack.size() < 3)
      throw new IllegalStateException("Error on line (" + lines + "): Missing operands for binary expression!");
    
    // If it can be, check if its operands are constants
    programStack.pop();
    String rhs = programStack.pop();
    String lhs = programStack.pop();
    Instruction rhI = Instruction.fromString(rhs);
    Instruction lhI = Instruction.fromString(lhs);

    if(lhI != Instruction.CNT || rhI != Instruction.CNT){
      // If its operands are not constants, revert the stack
      programStack.push(lhs);
      programStack.push(rhs);
      programStack.push(instruction.toString());
      return;
    }
    // Otherwise calculate the instruction with its constant operands and push the result on the stack
    int result = evalInstruction(rhs, lhs, instruction);
    programStack.push(Integer.toString(result));
  }

  public void replaceExpression(int value){
    System.out.println("Removing top 3");
    programStack.pop();
    programStack.pop();
    programStack.pop();
    System.out.println("PUSHING: " + value);
    programStack.push(Integer.toString(value));
  }

  private int evalInstruction(String rhss, String lhss, Instruction instruction){
    int rhs = Integer.parseInt(rhss);
    int lhs = Integer.parseInt(lhss);

    switch(instruction){
      case ADD:
        return lhs + rhs;
      case SUB:
        return lhs - rhs;
      case MUL:
        return lhs * rhs;
      case MOD:
        return lhs % rhs;
      case DIV:
        return lhs / rhs;
      case PWR:
        if(rhs < 0){
          throw new IllegalArgumentException("Error on line (" + lines + "): Cannot raise to a negative power!");
        } 
        return (int) Math.pow(lhs, rhs);
      default: // Wont happen
        throw new IllegalArgumentException("Error on line (" + lines + "): Not supported instruction: " + instruction.toString());
    }
  }

  public String toString(){
    return programStack.toString();
  }


}
