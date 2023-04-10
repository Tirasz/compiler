package ast;

public class UnaryExpression extends Expression{

  private Expression expression;
  private Operator op;

  public UnaryExpression(Expression expr, String opToken){
    this.expression = expr;
    this.op = Operator.fromString(opToken);
  }

  @Override
  public void eval(Program p) {
    p.addStrLine("0");
    expression.eval(p);
    p.addStrLine(op.toString());
  }

  public String toString(){
    return (this.op == Operator.ADD ? "" : "-") + this.expression;
  }
  
}
