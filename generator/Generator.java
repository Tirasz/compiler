package generator;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {
  
  private ArrayList<String> instructions = new ArrayList<String>();
  private Stack<String> idk = new Stack<String>();


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
    if(idk.size() < 3)
      throw new IllegalStateException("Binary expression on top of stack, but not enough operands!");
    
    // If it can be, check its operands are constants
    instruction = Instruction.fromString(idk.pop());
    String rhs = idk.pop();
    String lhs = idk.pop();
    if(Instruction.fromString(lhs) != Instruction.CNT || Instruction.fromString(rhs) != Instruction.CNT){
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
        return (int) Math.pow(lhs, rhs);
      default:
        throw new IllegalArgumentException("Not supported instruction: " + instruction.toString());
    }
  }

  public String toString(){
    return instructions.toString() + "\n" + idk.toString();
  }


}
