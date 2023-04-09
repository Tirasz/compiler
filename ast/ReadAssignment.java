package ast;

public class ReadAssignment extends Expression{

  private Expression index;

  public ReadAssignment(Expression index){
    this.index = index;
  }

  @Override
  public int eval(Program prog) {
    this.index.eval(prog);
    prog.addStrLine(Operator.RDA.toString()); 
    return 0;
  }

  public String toString(){
    return "m[" + index + "] = <INPUT>";
  }
  
}
