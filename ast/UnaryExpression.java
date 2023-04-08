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
    switch(this.op){
      case ADD:
        return this.expression.eval(p);
      case SUB:
        return -this.expression.eval(p);
      default:
        throw new IllegalArgumentException("UnaryExpression node initialised with: " + this.op );
    }
  }
  
}
