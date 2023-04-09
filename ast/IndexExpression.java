package ast;

public class IndexExpression extends Expression {
  private Expression index;

  public IndexExpression(Expression index){
    this.index = index;
  }

  @Override
  public void eval(Program prog) {
    this.index.eval(prog);
    prog.addStrLine(Operator.INE.toString());
  }

  public String toString() {
    return "m[" + index + "]";
  }
  
}
