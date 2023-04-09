package ast;

public class BinaryExpression extends Expression{

  private Expression lhs;
  private Expression rhs;
  private Operator op;

  public BinaryExpression(Expression lhs, Expression rhs, String opToken){
    this.lhs = lhs;
    this.rhs = rhs;
    this.op = Operator.fromString(opToken);
  }

  @Override
  public void eval(Program p) {
    lhs.eval(p);
    rhs.eval(p);
    p.addStrLine(op.toString());
  }
  
  public String toString() {
    return lhs + op.toString() + rhs;
  }
}
