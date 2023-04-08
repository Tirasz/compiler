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
    int index = this.index.eval(prog);
    int value = this.value.eval(prog);
    //System.out.println("Memory at index: " + Integer.toString(index) + " Should be: " + Integer.toString(value)); 
    prog.addStrLine("[]=");
    return 0;
  }

  public String toString() {
    return "m[" + index + "] = " + value;
  }
  
}
