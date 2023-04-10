package ast;

public class ConstExpression extends Expression{
  private int value;

  public ConstExpression(String token){
    this.value = Integer.parseInt(token);
  }

  @Override
  public void eval(Program prog) {
    prog.addStrLine(Integer.toString(value));
  }

  public String toString() {
    return Integer.toString(value);
  }
}
