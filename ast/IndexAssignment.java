package ast;

public class IndexAssignment extends Expression{

  private Expression index;
  private Expression value;

  public IndexAssignment(Expression index, Expression value){
    this.index = index;
    this.value = value;
  }

  @Override
  public int eval(Program prog) {
    this.index.eval(prog);
    this.value.eval(prog);
    prog.addStrLine(Operator.INA.toString());
    return 0;
  }

  public String toString() {
    return "m[" + index + "] = " + value;
  }
  
}
