package ast;

public class UnaryExpression extends Expression{

  private Expression expression;
  private Operator op;

  public UnaryExpression(Expression expr, String opToken){
    this.expression = expr;
    this.op = Operator.fromString(opToken);
  }

  @Override
  public int eval(Program p) {
    int value;
    p.addStrLine("0");
    switch(this.op){
      case ADD:
        value = this.expression.eval(p);
        break;
      case SUB:
        value = -this.expression.eval(p);
        break;
      default:
        throw new IllegalArgumentException("UnaryExpression node initialised with: " + this.op );
    }

    p.addStrLine(op.toString());
    return value;
  }

  public String toString(){
    return (this.op == Operator.ADD ? "" : "-") + this.expression;
  }
  
}
