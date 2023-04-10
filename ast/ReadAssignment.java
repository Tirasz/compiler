package ast;

public class ReadAssignment extends Expression{

  private Expression index;

  public ReadAssignment(Expression index){
    this.index = index;
  }

  @Override
  public void eval(Program prog) {
    index.eval(prog);
    prog.addStrLine(Operator.RDA.toString()); 
  }

  public String toString(){
    return "read(" + index + ")";
  }
  
}
