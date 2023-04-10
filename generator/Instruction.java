package generator;

import java.util.Map;
import static java.util.Map.entry;  

public enum Instruction {
  CNT("CONST"),
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  MOD("%"),
  PWR("^"),
  MEMR("[]"),     // Index expression, m val at index
  MEMA("[]="),   // Index assignment, m val at index = value
  READ("()"),   // Read assignment, m val at index = INPUT
  EXP(";"),   // Eval and print expression
  ASS(";;"); // nothing to eval, assignment

  private static Map<Instruction, String> opCodes = Map.ofEntries(
    entry(CNT, "0000"),
    entry(ADD, "0001"),
    entry(SUB, "0010"),
    entry(MUL, "0011"),
    entry(DIV, "0100"),
    entry(MOD, "0101"),
    entry(PWR, "0110"),
    entry(MEMA, "0111"),
    entry(MEMR, "1000"),
    entry(READ, "1001" + "0111"), // read(x) => m[x] = input() --> push x, push input, mema
    entry(EXP, "1010"),
    entry(ASS, "") // skip
  );


  private String token;
 
  Instruction(String token){
    this.token = token;
  }

  public String toString(){
    return token;
  }

  private static Instruction fromString(String string){
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

  public static String toFixedLengthBinary(int x, int len){ // Need payload to be fix length, copied this from god knows where
        StringBuilder result = new StringBuilder();
 
        for (int i = len - 1; i >= 0 ; i--){
            int mask = 1 << i;
            result.append((x & mask) != 0 ? 1 : 0);
        }
 
        return result.toString();
    }

  public static String toOpCode(String token){
    Instruction instruction = Instruction.fromString(token);
    StringBuilder opcode = new StringBuilder();

    opcode.append(Instruction.opCodes.get(instruction));

    if(instruction == CNT){
      int value = Integer.parseInt(token);
      String valueInBinary = Integer.toBinaryString(value);
      String lenghtOfValueInBinary = Instruction.toFixedLengthBinary(valueInBinary.length(), 6);
      opcode.append(lenghtOfValueInBinary);
      opcode.append(valueInBinary);
    }
    return opcode.toString();
  }

}