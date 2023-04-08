package ast;

public class Const extends Expression{
  private int value;

  public Const(String token){
    this.value = Integer.parseInt(token);
  }

  @Override
  public int eval(Program prog) {
    return this.value;
  }

}
