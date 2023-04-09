package ast;

public class IndexExpression extends Expression {
  private Expression index;

  public IndexExpression(Expression index){
    this.index = index;
  }

  @Override
  public int eval(Program prog) {
    this.index.eval(prog);
    prog.addStrLine(Operator.INE.toString());
    return 0;
  }

  public String toString() {
    return "val(m[" + index + "])";
  }
  
}
