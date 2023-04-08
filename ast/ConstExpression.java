package ast;

public class ConstExpression extends Expression{
  private int value;

  public ConstExpression(String token){
    this.value = Integer.parseInt(token);
  }

  @Override
  public int eval(Program prog) {
    prog.addStrLine(Integer.toString(value));
    return this.value;
  }

  public String toString() {
    return Integer.toString(value);
  }
}
