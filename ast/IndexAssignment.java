package ast;

public class IndexAssignment extends Expression{

  private Expression index;
  private Expression value;

  public IndexAssignment(Expression index, Expression value){
    this.index = index;
    this.value = value;
  }

  @Override
  public void eval(Program prog) {
    index.eval(prog);
    value.eval(prog);
    prog.addStrLine(Operator.INA.toString());
  }

  public String toString() {
    return "m[" + index + "] = " + value;
  }
  
}
