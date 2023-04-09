package generator;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {
  
  private ArrayList<String> instructions = new ArrayList<String>();
  private Stack<String> idk = new Stack<String>();
  public int lines = 0;

  public void addInstruction(String inst){
    instructions.add(inst);
    idk.push(inst);
    tryEvalTop();
  }

  private void tryEvalTop(){
    // Check if the top instruction can be evaluated (basically not a constant or memory access / assignment)
    Instruction instruction = Instruction.fromString(idk.peek());
    if(!instruction.isEvaluable())
      return;
    
    // Instead of emptyStackException :)
    if(idk.size() < 3)
      throw new IllegalStateException("Error on line (" + lines + "): Missing operands for binary expression!");
    
    // If it can be, check if its operands are constants
    idk.pop();
    String rhs = idk.pop();
    String lhs = idk.pop();
    Instruction rhI = Instruction.fromString(rhs);
    Instruction lhI = Instruction.fromString(lhs);

    if(lhI != Instruction.CNT || rhI != Instruction.CNT){
      // If its operands are not constants, revert the stack
      idk.push(lhs);
      idk.push(rhs);
      idk.push(instruction.toString());
      return;
    }
    // Otherwise calculate the instruction with its constant operands and push the result on the stack
    int result = evalInstruction(rhs, lhs, instruction);
    idk.push(Integer.toString(result));
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
    return instructions.toString() + "\n" + idk.toString();
  }


}
