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
  public int eval(Program p) {
   switch(this.op){
    case ADD:
      return lhs.eval(p) + rhs.eval(p);
    case SUB:
      return lhs.eval(p) - rhs.eval(p);
    case MUL:
      return lhs.eval(p) * rhs.eval(p);
    case DIV:
      return lhs.eval(p) / rhs.eval(p);
    case MOD:
      return lhs.eval(p) % rhs.eval(p);
    case PWR:
      return (int) Math.pow(lhs.eval(p), rhs.eval(p));
    default:
      throw new IllegalArgumentException("Add node initialised with: " + this.op );
   }
  }
  
  
}
