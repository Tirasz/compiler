package ast;

public enum Operator {
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  MOD("%"),
  PWR("^"),
  INE("[]"), // Index expression, m val at index
  INA("[]="), // Index assignment, m val at index = value
  RDA("()"); // Read assignment, m val at index = INPUT

  private String token;

  Operator(String token){
    this.token = token;
  }

  public String toString(){
    return token;
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