package generator;

import java.util.Set;

public enum Instruction {
  CNT("CONST"),
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  MOD("%"),
  PWR("^"),
  INE("[]"),     // Index expression, m val at index
  INA("[]="),   // Index assignment, m val at index = value
  RDA("()"),   // Read assignment, m val at index = INPUT
  EXP(";"),   // Eval and print expression
  ASS(";;"); // nothing to eval, assignment

  private static Set<Instruction> evaluableOps = Set.of(ADD, SUB, MUL, DIV, MOD, PWR);

  private String token;
 
  Instruction(String token){
    this.token = token;
  }

  public String toString(){
    return token;
  }

  public boolean isEvaluable(){
    return evaluableOps.contains(this);
  }

  public static Instruction fromString(String string){
    for(Instruction t: Instruction.values()){
      if(t.token.equals(string)){
        return t;
      }
    }

    try{
      Integer.parseInt(string);
      return CNT;
    }
    catch(NumberFormatException e){
      throw new IllegalArgumentException("No operator with token " + string + " found");
    }
  }

}