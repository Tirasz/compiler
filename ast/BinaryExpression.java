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
    int value;
   switch(this.op){
    case ADD:
      value = lhs.eval(p) + rhs.eval(p);
      break;
    case SUB:
      value = lhs.eval(p) - rhs.eval(p);
      break;
    case MUL:
      value = lhs.eval(p) * rhs.eval(p);
      break;
    case DIV:
      value = lhs.eval(p) / rhs.eval(p);
      break;
    case MOD:
      value =  lhs.eval(p) % rhs.eval(p);
      break;
    case PWR:
      value = (int) Math.pow(lhs.eval(p), rhs.eval(p));
      break;
    default:
      throw new IllegalArgumentException("Add node initialised with: " + this.op );
   }
   p.addStrLine(op.toString());
   return value;
  }
  
  public String toString() {
    return lhs + op.toString() + rhs;
  }
}
