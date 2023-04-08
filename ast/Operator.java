package ast;

public enum Operator {
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  MOD("%"),
  PWR("^");

  private String token;

  Operator(String token){
    this.token = token;
  }

  public static Operator fromString(String string){
    for(Operator t: Operator.values()){
      if(t.token.equals(string)){
        return t;
      }
    }
    throw new IllegalArgumentException("No operator with token " + string + " found");
  }

}